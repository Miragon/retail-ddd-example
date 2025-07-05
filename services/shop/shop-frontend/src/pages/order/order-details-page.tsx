import React from "react";
import {
    Alert,
    Box,
    Button,
    Chip,
    CircularProgress,
    Divider,
    List,
    ListItem,
    ListItemText,
    Paper,
    Typography
} from "@mui/material";
import {CheckCircle as CheckCircleIcon, Receipt as ReceiptIcon} from "@mui/icons-material";
import {useOrder, getOrderStatusColor, formatOrderDate} from "../../hooks/order.hook";
import {useNavigate, useParams} from "react-router";

export function OrderDetailsPage() {
    const {orderId} = useParams<{ orderId: string }>();
    const {data: order, status, error} = useOrder(orderId!);
    const navigate = useNavigate();

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
                Error loading order: {(error as Error).message}
            </Alert>
        );
    }

    if (!order) {
        return (
            <Alert severity="warning">
                Order not found
            </Alert>
        );
    }

    const orderData = order;

    return (
        <Box maxWidth="800px" margin="0 auto" padding="2rem">
            <Box display="flex" alignItems="center" mb={3}>
                <CheckCircleIcon sx={{mr: 2, color: 'success.main', fontSize: 32}}/>
                <Typography variant="h4" component="h1">
                    Order Confirmed!
                </Typography>
            </Box>

            <Paper elevation={2} sx={{p: 3, mb: 3}}>
                <Box display="flex" justifyContent="space-between" alignItems="center" mb={2}>
                    <Typography variant="h6">
                        <ReceiptIcon sx={{mr: 1, verticalAlign: 'middle'}}/>
                        Order #{orderData.id.slice(-8).toUpperCase()}
                    </Typography>
                    <Chip
                        label={orderData.status}
                        color={getOrderStatusColor(orderData.status) as any}
                        variant="outlined"
                    />
                </Box>

                <Typography variant="body2" color="textSecondary" gutterBottom>
                    Order Date: {formatOrderDate(orderData.orderDate)}
                </Typography>
            </Paper>

            <Paper elevation={2} sx={{mb: 3}}>
                <Typography variant="h6" sx={{p: 2, pb: 0}}>
                    Order Items
                </Typography>
                <List>
                    {orderData.items.map((item, index) => (
                        <React.Fragment key={item.articleId}>
                            <ListItem>
                                <ListItemText
                                    primary={item.articleName}
                                    secondary={
                                        <Box>
                                            <Typography variant="body2" color="textSecondary" component="div">
                                                Quantity: {item.quantity} × {item.pricePerItem.toFixed(2)} €
                                            </Typography>
                                            <Typography variant="body2" component="div" sx={{fontWeight: 'bold'}}>
                                                Subtotal: {item.totalPrice.toFixed(2)} €
                                            </Typography>
                                        </Box>
                                    }
                                    secondaryTypographyProps={{ component: 'div' }}
                                />
                            </ListItem>
                            {index < orderData.items.length - 1 && <Divider/>}
                        </React.Fragment>
                    ))}
                </List>

                <Divider/>

                <Box sx={{p: 2}}>
                    <Box display="flex" justifyContent="space-between" alignItems="center">
                        <Typography variant="h6">
                            Total Amount:
                        </Typography>
                        <Typography variant="h6" sx={{fontWeight: 'bold'}}>
                            {orderData.totalAmount.toFixed(2)} €
                        </Typography>
                    </Box>
                </Box>
            </Paper>

            <Alert severity="success" sx={{mb: 3}}>
                <Typography variant="body2">
                    Thank you for your order! You will receive a confirmation email shortly.
                    Your order is being processed and will be shipped soon.
                </Typography>
            </Alert>

            <Box display="flex" gap={2} justifyContent="center">
                <Button
                    variant="contained"
                    color="primary"
                    onClick={handleContinueShopping}>
                    Continue Shopping
                </Button>
            </Box>
        </Box>
    );
}