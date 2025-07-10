import {Route, Routes} from "react-router";
import {ArticleOverviewPage} from "../pages/article-overview/article-overview-page.tsx";
import {ArticleDetailsPage} from "../pages/article-details/article-details-page.tsx";
import {CartPage} from "../pages/cart/cart-page.tsx";
import {OrderDetailsPage} from "../pages/order/order-details-page.tsx";
import {OrdersOverviewPage} from "../pages/order/orders-overview-page.tsx";

/**
 * This component contains the routes for the application.
 * Each route represents a page in the application.
 * @constructor
 */
export const ApplicationRoutes = () => (
    <Routes>
        <Route path="/" element={<ArticleOverviewPage/>}/>
        <Route path="/articles" element={<ArticleOverviewPage/>}/>
        <Route path="/articles/:articleId" element={<ArticleDetailsPage/>}/>
        <Route path="/cart" element={<CartPage/>}/>
        <Route path="/orders" element={<OrdersOverviewPage/>}/>
        <Route path="/orders/:orderId" element={<OrderDetailsPage/>}/>
    </Routes>
)
