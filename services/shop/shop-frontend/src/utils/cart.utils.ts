import {CartDto} from "../api";

export const getCartItemCount = (cart: CartDto | undefined): number => {
    if (!cart || !cart.items) return 0;
    return cart.items.reduce((total, item) => total + item.quantity, 0);
};

export const isCartEmpty = (cart: CartDto | undefined): boolean => {
    return !cart || !cart.items || cart.items.length === 0;
};