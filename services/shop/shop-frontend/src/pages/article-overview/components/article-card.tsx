import {Alert, Button, Card, CardActions, CardContent, Snackbar, Typography} from "@mui/material";
import React, {useState} from "react";
import {ArticleDto} from "../../../api";
import {useAddToCart} from "../../../hooks/cart.hook";

interface ArticleProps {
    article: ArticleDto
    clickOnCard: (articleId: string) => void
}

export const ArticleCard: React.FC<ArticleProps> = props => {
    const {article} = props;
    const addToCartMutation = useAddToCart();
    const [showSuccess, setShowSuccess] = useState(false);

    const handleAddToCart = (event: React.MouseEvent) => {
        event.stopPropagation();
        addToCartMutation.mutate({articleId: article.id,}, {
            onSuccess: () => {
                setShowSuccess(true);
            }
        });
    };

    const handleCloseSnackbar = () => {
        setShowSuccess(false);
    };

    return (
        <>
            <Card
                elevation={2}
                sx={{
                    height: '100%',
                    display: 'flex',
                    flexDirection: 'column',
                    cursor: 'pointer',
                    '&:hover': {
                        boxShadow: 3
                    }
                }}
                onClick={() => props.clickOnCard(article.id)}>
                <CardContent sx={{flexGrow: 1}}>
                    <Typography variant="h6" component="div" gutterBottom>
                        {article.name}
                    </Typography>
                    <Typography variant="body2" color="textSecondary" gutterBottom>
                        {article.description}
                    </Typography>
                    <Typography variant="h6" sx={{fontWeight: 'bold', mt: 2}}>
                        {article.price.toFixed(2)} €
                    </Typography>
                </CardContent>
                <CardActions>
                    <Button
                        variant="contained"
                        color="primary"
                        onClick={handleAddToCart}
                        disabled={addToCartMutation.isPending}
                        fullWidth>
                        {addToCartMutation.isPending ? 'Adding...' : 'Add to Cart'}
                    </Button>
                </CardActions>
            </Card>

            <Snackbar
                open={showSuccess}
                autoHideDuration={3000}
                onClose={handleCloseSnackbar}
                anchorOrigin={{vertical: 'bottom', horizontal: 'center'}}>
                <Alert onClose={handleCloseSnackbar} severity="success" sx={{width: '100%'}}>
                    Added "{article.name}" to cart!
                </Alert>
            </Snackbar>
        </>
    )
}