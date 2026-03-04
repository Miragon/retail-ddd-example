import {useMutation, useQuery, useQueryClient} from '@tanstack/react-query';
import {CompleteOrderControllerApi, GetOrderControllerApi, GetOrdersControllerApi, OrderDto} from '../api/api';
import {apiExec} from "../shared/api/api-exec.ts";

export const useCompleteOrder = () => {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: async () => {
            return apiExec(CompleteOrderControllerApi, api => api.completeOrder());
        },
        onSuccess: () => {
            queryClient.invalidateQueries({queryKey: ['cart']});
            queryClient.invalidateQueries({queryKey: ['orders']});
        },
        onError: (error) => {
            console.error('Error completing order:', error);
        },
    });
};

export const useOrders = () => {
    return useQuery({
        queryKey: ['orders'],
        queryFn: async () => {
            return apiExec(GetOrdersControllerApi, api => api.getOrdersByUser());
        },
        staleTime: 60000,
    });
};

export const useOrder = (orderId: string) => {
    return useQuery({
        queryKey: ['order', orderId],
        queryFn: async () => {
            return apiExec(GetOrderControllerApi, api => api.getOrder(orderId));
        },
        enabled: !!orderId,
        staleTime: 60000,
    });
};

export const getOrderStatusColor = (status: string) => {
    switch (status.toLowerCase()) {
        case 'pending':
            return 'warning';
        case 'confirmed':
            return 'info';
        case 'shipped':
            return 'primary';
        case 'delivered':
            return 'success';
        case 'cancelled':
            return 'error';
        default:
            return 'default';
    }
};

export const formatOrderDate = (orderDate: string) => {
    return new Date(orderDate).toLocaleDateString('en-US', {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    });
};

export const getOrderTotalItems = (order: OrderDto): number => {
    if (!order || !order.items) return 0;
    return order.items.reduce((total, item) => total + item.quantity, 0);
};
