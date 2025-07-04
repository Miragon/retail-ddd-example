import {Alert, Box, Button, CircularProgress, Paper, Snackbar, Typography} from "@mui/material";
import {ArrowBack as ArrowBackIcon, ShoppingCart as ShoppingCartIcon, Store as StoreIcon} from "@mui/icons-material";
import {useArticles} from "../../hooks/articles.hook.ts";
import React, {useMemo, useState} from "react";
import {useNavigate, useParams} from "react-router";
import {ArticleDto} from "../../api";
import {useAddToCart} from "../../hooks/cart.hook";

export const ArticleDetailsPage: React.FC = () => {
    const {articleId} = useParams<{ articleId: string }>();
    const {data, status, error} = useArticles();
    const addToCartMutation = useAddToCart();
    const [showSuccess, setShowSuccess] = useState(false);
    const navigate = useNavigate();

    const article = useMemo((): ArticleDto | undefined => {
        const matchingArticle = data?.find((article) => article.id === articleId)
        return matchingArticle ?? undefined
    }, [articleId, data])

    const handleAddToCart = () => {
        if (!article) return;
        addToCartMutation.mutate({articleId: article.id,}, {
            onSuccess: () => {
                setShowSuccess(true);
            }
        });
    };

    const handleCloseSnackbar = () => {
        setShowSuccess(false);
    };

    const handleBackToArticles = () => {
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
                Error loading article: {(error as Error).message}
            </Alert>
        );
    }

    if (!article) {
        return (
            <Alert severity="warning">
                Article not found
            </Alert>
        );
    }

    return (
        <>
            <Box maxWidth="800px" margin="0 auto" padding="2rem">
                <Button
                    startIcon={<ArrowBackIcon/>}
                    onClick={handleBackToArticles}
                    sx={{mb: 3}}
                    variant="outlined">
                    Back to Articles
                </Button>

                <Paper elevation={2} sx={{p: 4}}>
                    <Box display="flex" alignItems="center" mb={3}>
                        <StoreIcon sx={{mr: 2, color: 'primary.main', fontSize: 32}}/>
                        <Typography variant="h4" component="h1">
                            {article.name}
                        </Typography>
                    </Box>

                    <Typography variant="body1" color="textSecondary" gutterBottom sx={{mb: 3}}>
                        {article.description}
                    </Typography>

                    <Box display="flex" justifyContent="space-between" alignItems="center" mb={3}>
                        <Typography variant="h5" sx={{fontWeight: 'bold'}}>
                            {article.price.toFixed(2)} €
                        </Typography>
                    </Box>

                    <Box display="flex" gap={2} justifyContent="center">
                        <Button
                            variant="contained"
                            color="primary"
                            startIcon={<ShoppingCartIcon/>}
                            onClick={handleAddToCart}
                            disabled={addToCartMutation.isPending}
                            size="large">
                            {addToCartMutation.isPending ? 'Adding...' : 'Add to Cart'}
                        </Button>
                    </Box>
                </Paper>

                {addToCartMutation.isError && (
                    <Alert severity="error" sx={{mt: 2}}>
                        Error adding to cart: {(addToCartMutation.error as Error).message}
                    </Alert>
                )}
            </Box>

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