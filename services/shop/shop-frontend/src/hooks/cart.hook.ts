import {useMutation, useQuery, useQueryClient} from '@tanstack/react-query';
import {AddToCartControllerApi, AddToCartRequest, GetCartControllerApi, RemoveFromCartControllerApi} from '../api/api';
import {apiExec} from "../api/fetch.ts";

export const useCart = () => {
    return useQuery({
        queryKey: ['cart'],
        retry: false,
        staleTime: 30000,
        queryFn: async () => {
            const response = await apiExec(GetCartControllerApi, api => api.getCart());
            return response.data;
        },
    });
};

export const useAddToCart = () => {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: async (request: AddToCartRequest) => {
            const response = await apiExec(AddToCartControllerApi, api => api.addToCart(request));
            return response.data;
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
            const response = await apiExec(RemoveFromCartControllerApi, api => api.removeFromCart(articleId));
            return response.data;
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
