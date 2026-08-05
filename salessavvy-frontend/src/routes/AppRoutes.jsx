import { Routes, Route } from "react-router-dom";

import UserRoute from "./UserRoute";

import UserLayout from "../layouts/UserLayout";
import AdminLayout from "../layouts/AdminLayout";

import Home from "../user/pages/Home";
import Login from "../user/pages/Login";
import Register from "../user/pages/Register";
import ProductDetails from "../user/pages/ProductDetails";
import Cart from "../user/pages/Cart";
import Orders from "../user/pages/Orders";
import OrderDetails from "../user/pages/OrderDetails";
import Profile from "../user/pages/Profile";

import AdminLogin from "../admin/pages/AdminLogin";
import Categories from "../admin/pages/Categories";
import Dashboard from "../admin/pages/Dashboard";
import AdminOrders from "../admin/pages/AdminOrders";
import Products from "../admin/pages/Products";
import AdminRoute from "./AdminRoute";
import NewProduct from "../admin/pages/NewProduct";
import EditProduct from "../admin/pages/EditProduct";
import NewCategory from "../admin/pages/NewCategory";
import EditCategory from "../admin/pages/EditCategory";
import AdminOrderDetails from "../admin/pages/AdminOrderDetails";

function AppRoutes() {
    return (
        <Routes>
            <Route element={<UserLayout />}>
                <Route path="/" element={<Home />} />
                <Route path="/login" element={<Login />} />
                <Route path="/register" element={<Register />} />
                <Route path="/products/:id" element={<ProductDetails />} />
                <Route path="/cart" element={
                    <UserRoute role="CUSTOMER">
                        <Cart />
                    </UserRoute>} />
                <Route path="/orders" element={
                    <UserRoute role="CUSTOMER">
                        <Orders />
                    </UserRoute>} />
                <Route path="/orders/:orderId" element={
                    <UserRoute role="CUSTOMER">
                        <OrderDetails />
                    </UserRoute>} />
                <Route path="/profile" element={
                    <UserRoute role="CUSTOMER">
                        <Profile />
                    </UserRoute>
                } />
            </Route>


            <Route path="/admin/login" element={<AdminLogin />} />
            <Route element={<AdminRoute />}>
                <Route element={<AdminLayout />}>
                    <Route path="/admin/dashboard" element={<Dashboard />} />
                    <Route path="/admin/orders" element={<AdminOrders />} />
                    <Route path="/admin/products" element={<Products />} />
                    <Route path="/admin/products/new" element={<NewProduct />} />
                    <Route path="/admin/products/edit/:id" element={<EditProduct />} />
                    <Route path="/admin/categories" element={<Categories />} />
                    <Route path="/admin/categories/new" element={<NewCategory />} />
                    <Route path="/admin/categories/edit/:id" element={<EditCategory />} />
                    <Route path="/admin/orders/:id" element={<AdminOrderDetails />} />
                </Route>
            </Route>
        </Routes>
    );
}

export default AppRoutes;