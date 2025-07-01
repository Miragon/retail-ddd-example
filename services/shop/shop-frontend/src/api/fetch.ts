import {BASE_PATH, BaseAPI} from "./base.ts";
import {Configuration} from "./configuration.ts";
import {AxiosResponse} from "axios";

export interface FailedApiResponse {
    error: string;
    data: undefined;
}

interface SuccessfulApiResponse<T> {
    data: T;
    error: undefined;
}

export type ApiResponse<T> = SuccessfulApiResponse<T> | FailedApiResponse;

export const apiExec = async <API extends BaseAPI, T>(
    API: new (config: Configuration) => API,
    execute: (api: API) => Promise<AxiosResponse<T>>
): Promise<ApiResponse<T>> => {
    try {
        const config = await getClientConfig();
        const response = await execute(new API(config));
        if (response.status >= 200 && response.status < 300) {
            return {data: response.data, error: undefined};
        } else {
            return {error: `${response.status}`, data: undefined};
        }
    } catch (error) {
        return {error: `${error}`, data: undefined,}
    }
};

/**
 * Currently we don't have a proxy for the local development.
 * Thus, we use the port to differentiate between local and production.
 * This should be replaced with a proper proxy setup in the future.
 */
const getClientConfig = async (): Promise<Configuration> => {
    const token = localStorage.getItem("dddToken");
    const basePath = window.location.port === '8080' ? '' : BASE_PATH;
    return new Configuration({
        basePath: basePath,
        baseOptions: {
            "headers": {
                "Authorization": `Bearer ${token}`,
            }
        }
    })
}