import {
    Alert,
    Box,
    Button,
    Card,
    CardActions,
    CardContent,
    Chip,
    CircularProgress,
    Typography
} from "@mui/material";
import {Receipt as ReceiptIcon, ShoppingBag as ShoppingBagIcon} from "@mui/icons-material";
import {formatOrderDate, getOrderStatusColor, getOrderTotalItems, useOrders} from "../../hooks/order.hook";
import {useNavigate} from "react-router";
import {If} from "../../shared/if";

export function OrdersOverviewPage() {
    const {data: orders, status, error} = useOrders();
    const navigate = useNavigate();

    const handleViewOrder = (orderId: string) => {
        navigate(`/orders/${orderId}`);
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
                Error loading orders: {(error as Error).message}
            </Alert>
        );
    }

    const hasOrders = Boolean(orders && orders.length > 0);

    return (
        <Box maxWidth="1200px" margin="0 auto" padding="2rem">
            <Typography variant="h4" component="h1" gutterBottom>
                <ShoppingBagIcon sx={{mr: 2, verticalAlign: 'middle'}}/>
                Your Orders
            </Typography>

            <If condition={!hasOrders}>
                <Box
                    data-testid="Orders-Overview-Empty"
                    display="flex" 
                    flexDirection="column" 
                    alignItems="center" 
                    justifyContent="center" 
                    minHeight="400px"
                    textAlign="center"
                >
                    <ReceiptIcon sx={{fontSize: 80, color: 'grey.400', mb: 3}}/>
                    <Typography variant="h6" color="textSecondary" gutterBottom>
                        No orders yet
                    </Typography>
                    <Typography variant="body2" color="textSecondary" gutterBottom sx={{mb: 3}}>
                        When you place an order, it will appear here.
                    </Typography>
                    <Button
                        data-testid="Orders-Overview-Empty-Button-ContinueShopping"
                        variant="contained"
                        color="primary"
                        onClick={handleContinueShopping}
                        size="large"
                    >
                        Start Shopping
                    </Button>
                </Box>
            </If>

            <If condition={hasOrders}>
                <Box
                    data-testid="Orders-Overview"
                    display="grid" 
                    gridTemplateColumns={{
                        xs: 'repeat(1, 1fr)',
                        md: 'repeat(2, 1fr)', 
                        lg: 'repeat(3, 1fr)'
                    }}
                    gap={3}
                >
                    {orders?.map((order) => (
                        <Card key={order.id} elevation={2} sx={{height: '100%', display: 'flex', flexDirection: 'column'}}>
                            <CardContent sx={{flexGrow: 1}}>
                                <Box display="flex" justifyContent="space-between" alignItems="center" mb={2}>
                                    <Typography variant="h6" component="div">
                                        <ReceiptIcon sx={{mr: 1, verticalAlign: 'middle', fontSize: 20}}/>
                                        #{order.id.slice(-8).toUpperCase()}
                                    </Typography>
                                    <Chip
                                        label={order.status}
                                        color={getOrderStatusColor(order.status) as any}
                                        variant="outlined"
                                        size="small"
                                    />
                                </Box>

                                <Typography variant="body2" color="textSecondary" gutterBottom>
                                    {formatOrderDate(order.orderDate)}
                                </Typography>

                                <Typography variant="body2" color="textSecondary" gutterBottom>
                                    {getOrderTotalItems(order)} item{getOrderTotalItems(order) !== 1 ? 's' : ''}
                                </Typography>

                                <Typography variant="h6" sx={{mt: 2, fontWeight: 'bold'}}>
                                    {order.totalAmount.toFixed(2)} €
                                </Typography>
                            </CardContent>

                            <CardActions>
                                <Button
                                    data-testid={`Orders-Overview-Button-ViewOrder-${order.id}`}
                                    size="small"
                                    variant="outlined"
                                    onClick={() => handleViewOrder(order.id)}
                                    fullWidth
                                >
                                    View Details
                                </Button>
                            </CardActions>
                        </Card>
                    ))}
                </Box>

                <Box display="flex" justifyContent="center" mt={4}>
                    <Button
                        data-testid="Orders-Overview-Button-ContinueShopping"
                        variant="contained"
                        color="primary"
                        onClick={handleContinueShopping}
                    >
                        Continue Shopping
                    </Button>
                </Box>
            </If>
        </Box>
    );
}