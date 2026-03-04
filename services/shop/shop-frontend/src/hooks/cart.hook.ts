import {useMutation, useQuery, useQueryClient} from '@tanstack/react-query';
import {AddToCartControllerApi, AddToCartRequest, GetCartControllerApi, RemoveFromCartControllerApi} from '../api/api';
import {apiExec} from "../shared/api/api-exec.ts";

export const useCart = () => {
    return useQuery({
        queryKey: ['cart'],
        retry: false,
        staleTime: 30000,
        queryFn: async () => {
            return apiExec(GetCartControllerApi, api => api.getCart());
        },
    });
};

export const useAddToCart = () => {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: async (request: AddToCartRequest) => {
            return apiExec(AddToCartControllerApi, api => api.addToCart(request));
        },
        onSuccess: () => {
            queryClient.invalidateQueries({queryKey: ['cart']});
        },
        onError: (error) => {
            console.error('Error adding to cart:', error);
        },
    });
};

export const useRemoveFromCart = () => {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: async (articleId: string) => {
            return apiExec(RemoveFromCartControllerApi, api => api.removeFromCart(articleId));
        },
        onSuccess: () => {
            // Invalidate cart query to refetch updated cart
            queryClient.invalidateQueries({queryKey: ['cart']});
        },
        onError: (error) => {
            console.error('Error removing from cart:', error);
        },
    });
};
