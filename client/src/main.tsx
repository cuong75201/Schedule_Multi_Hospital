import { createRoot } from "react-dom/client";
import "./index.css";
import { createBrowserRouter, RouterProvider } from "react-router";
import UserLayout from "./layout/user/UserLayout";
import UserHomePage from "./page/userPage/HomePage";
import { StrictMode, Suspense } from "react";

const router = createBrowserRouter([
  {
    path: "/",
    Component: UserLayout,
    children: [{
      index:true,
      Component: UserHomePage,
    }],
  },
]);
createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <Suspense fallback={<div>Đang tải dữ liệu ...</div>}>
      <RouterProvider router={router} />
    </Suspense>
  </StrictMode>,
);
