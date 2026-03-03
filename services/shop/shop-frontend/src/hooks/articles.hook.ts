import {useQuery} from "@tanstack/react-query";
import {ArticleDto, LoadArticlesControllerApi} from "../api";
import {apiExec} from "../shared/api/api-exec.ts";

export const useArticles = () => {
    return useQuery({
        queryKey: ['articles'],
        queryFn: async (): Promise<Array<ArticleDto>> => {
            const apiResponse = await apiExec(LoadArticlesControllerApi, api => api.loadArticles())
            return apiResponse.data ?? []
        },
    })
}
