import React, {useCallback} from "react";
import {
    Alert,
    Box,
    Button,
    CircularProgress,
    Divider,
    IconButton,
    List,
    ListItem,
    ListItemText,
    Paper,
    Typography
} from "@mui/material";
import {Delete as DeleteIcon, ShoppingCart as ShoppingCartIcon} from "@mui/icons-material";
import {useCart, useRemoveFromCart} from "../../hooks/cart.hook";
import {useCompleteOrder} from "../../hooks/order.hook";
import {If} from "../../shared/if";
import {useNavigate} from "react-router";
import {getCartItemCount, isCartEmpty} from "../../utils/cart.utils.ts";
import {useArticles} from "../../hooks/articles.hook.ts";

export function CartPage() {

    const {data: cart, status, error} = useCart();
    const {data} = useArticles()

    const removeFromCartMutation = useRemoveFromCart();
    const completeOrderMutation = useCompleteOrder();
    const navigate = useNavigate();

    const getArticleNameById = useCallback((articleId: string): string => {
        const article = data?.find(a => a.id === articleId);
        return article ? article.name : `Unknown Article (${articleId})`;
    }, [data]);

    const handleRemoveFromCart = (articleId: string) => {
        removeFromCartMutation.mutate(articleId);
    };

    const handleCompleteOrder = () => {
        completeOrderMutation.mutate(undefined, {
            onSuccess: (order) => {
                !!order && navigate(`/orders/${order.id}`);
            }
        });
    };

    const handleContinueShopping = () => {
        navigate('/articles');
    };

    if (status === 'pending') {
        return (
            <Box display="flex" justifyContent="center" alignItems="center" minHeight="400px">
                <CircularProgress/>
            </Box>
        );
    }

    if (status === 'error') {
        return (
            <Alert severity="error">
                Error loading cart: {(error as Error).message}
            </Alert>
        );
    }
    
    return (
        <Box maxWidth="800px" margin="0 auto" padding="2rem">
            <Typography variant="h4" component="h1" gutterBottom>
                <ShoppingCartIcon sx={{mr: 2, verticalAlign: 'middle'}}/>
                Your Shopping Cart
            </Typography>

            <If condition={isCartEmpty(cart)}>
                <Paper elevation={2} sx={{p: 4, textAlign: 'center', mt: 4}}>
                    <ShoppingCartIcon sx={{fontSize: 64, color: 'grey.400', mb: 2}}/>
                    <Typography variant="h6" color="textSecondary" gutterBottom>
                        Your cart is empty
                    </Typography>
                    <Typography variant="body2" color="textSecondary" gutterBottom>
                        Add some items to get started!
                    </Typography>
                    <Button
                        variant="contained"
                        color="primary"
                        sx={{mt: 2}}
                        onClick={handleContinueShopping}>
                        Continue Shopping
                    </Button>
                </Paper>
            </If>

            <If condition={!isCartEmpty(cart)}>
                <Paper elevation={2} sx={{mt: 2}}>
                    <List>
                        {cart?.items.map((item, index) => (
                            <React.Fragment key={item.articleId}>
                                <ListItem
                                    secondaryAction={
                                        <IconButton
                                            sx={{mr: 0.5}}
                                            edge="end"
                                            aria-label="remove"
                                            onClick={() => handleRemoveFromCart(item.articleId)}
                                            disabled={removeFromCartMutation.isPending}
                                            color="error">
                                            <DeleteIcon/>
                                        </IconButton>
                                    }>
                                    <ListItemText
                                        primary={getArticleNameById(item.articleId)}
                                        secondary={
                                            <Box>
                                                <Typography variant="body2" color="textSecondary" component="div">
                                                    Quantity: {item.quantity}
                                                </Typography>
                                                <Typography variant="body2" color="textSecondary" component="div">
                                                    Price per item: {item.pricePerItem.toFixed(2)} €
                                                </Typography>
                                                <Typography variant="body2" component="div" sx={{fontWeight: 'bold'}}>
                                                    Total: {item.totalPrice.toFixed(2)} €
                                                </Typography>
                                            </Box>
                                        }
                                        secondaryTypographyProps={{ component: 'div' }}
                                    />
                                </ListItem>
                                {index < cart.items.length - 1 && <Divider/>}
                            </React.Fragment>
                        ))}
                    </List>

                    <Divider/>

                    <Box sx={{p: 3}}>
                        <Box display="flex" justifyContent="space-between" alignItems="center" mb={2}>
                            <Typography variant="body1">
                                Items in cart: {getCartItemCount(cart)}
                            </Typography>
                            <Typography variant="h6" component="span" sx={{fontWeight: 'bold'}}>
                                Total: {cart?.totalPrice.toFixed(2)} €
                            </Typography>
                        </Box>

                        <Box display="flex" gap={2} flexDirection={{xs: 'column', sm: 'row'}}>
                            <Button
                                variant="outlined"
                                onClick={handleContinueShopping}
                                sx={{flex: 1}}>
                                Continue Shopping
                            </Button>
                            <Button
                                variant="contained"
                                color="primary"
                                onClick={handleCompleteOrder}
                                disabled={completeOrderMutation.isPending}
                                sx={{flex: 1}}>
                                {completeOrderMutation.isPending ? 'Processing...' : 'Complete Order'}
                            </Button>
                        </Box>
                    </Box>
                </Paper>
            </If>

            {completeOrderMutation.isError && (
                <Alert severity="error" sx={{mt: 2}}>
                    Error completing order: {(completeOrderMutation.error as Error).message}
                </Alert>
            )}
        </Box>
    );
}