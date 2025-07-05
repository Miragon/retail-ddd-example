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

const getClientConfig = async (): Promise<Configuration> => {
    const token = localStorage.getItem("dddToken");
    return new Configuration({
        basePath: BASE_PATH,
        baseOptions: {
            "headers": {
                "Authorization": `Bearer ${token}`,
            }
        }
    })
}