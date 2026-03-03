import {AxiosResponse} from "axios";
import {BaseAPI} from "../../api/base.ts";
import {Configuration} from "../../api";
import {initRuntimeConfig} from "../runtime-config.ts";

export interface FailedApiResponse {
    error: string;
    data: undefined;
}

interface SuccessfulApiResponse<T> {
    data: T;
    error: undefined;
}

export type ApiResponse<T> = SuccessfulApiResponse<T> | FailedApiResponse;

type ApiCtor<API extends BaseAPI> = new (config: Configuration) => API;
type ApiExecute<API extends BaseAPI, T> = (api: API) => Promise<AxiosResponse<T>>;

const createApiConfiguration = async (): Promise<Configuration> => {
    const runtimeConfig = await initRuntimeConfig();
    const token = localStorage.getItem("dddToken");
    return new Configuration({
        basePath: runtimeConfig.shopBackendUrl,
        baseOptions: {
            headers: token ? {"Authorization": `Bearer ${token}`} : {},
        },
    });
}

export async function apiExec<API extends BaseAPI, T>(
    ApiClass: ApiCtor<API>,
    executeRequest: ApiExecute<API, T>
): Promise<ApiResponse<T>> {
    try {
        const config = await createApiConfiguration();
        const response = await executeRequest(new ApiClass(config));
        if (response.status >= 200 && response.status < 300) {
            return {data: response.data, error: undefined};
        } else {
            return {error: `${response.status}`, data: undefined};
        }
    } catch (error) {
        return {error: `${error}`, data: undefined};
    }
}
