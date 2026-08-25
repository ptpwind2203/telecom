import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { loginUser } from "../services/userService";
import "../css/Login.css";

function Login() {

    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        account: "",
        password: ""
    });

    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);

    const handleChange = (e) => {

        const { name, value } = e.target;

        setFormData({
            ...formData,
            [name]: value
        });
    };


    const handleSubmit = async (e) => {

        e.preventDefault();

        setError("");

        // Kiểm tra account
        if (!formData.account.trim()) {
            setError("Vui lòng nhập email hoặc số điện thoại.");
            return;
        }

        // Kiểm tra password
        if (!formData.password) {
            setError("Vui lòng nhập mật khẩu.");
            return;
        }

        try {

            setLoading(true);

            const result = await loginUser({
                account: formData.account.trim(),
                password: formData.password
            });

            console.log("Login thành công:", result);

            // Đăng nhập thành công
            navigate("/profile");

        } catch (err) {

            console.error("Login error:", err);

            if (err.response) {

                setError(
                    err.response.data?.message ||
                    "Tài khoản hoặc mật khẩu không chính xác."
                );

            } else {

                setError(
                    "Không thể kết nối đến server."
                );
            }

        } finally {

            setLoading(false);

        }
    };


    return (
        <div className="login-container">

            <div className="login-card">

                <h1>Đăng nhập</h1>

                <p className="login-subtitle">
                    Đăng nhập vào tài khoản của bạn
                </p>


                {error && (
                    <div className="login-error">
                        {error}
                    </div>
                )}


                <form onSubmit={handleSubmit}>

                    <div className="login-form-group">

                        <label htmlFor="account">
                            Email hoặc số điện thoại
                        </label>

                        <input
                            id="account"
                            type="text"
                            name="account"
                            value={formData.account}
                            onChange={handleChange}
                            placeholder="Nhập email hoặc số điện thoại"
                            autoComplete="username"
                            disabled={loading}
                        />

                    </div>


                    <div className="login-form-group">

                        <label htmlFor="password">
                            Mật khẩu
                        </label>

                        <input
                            id="password"
                            type="password"
                            name="password"
                            value={formData.password}
                            onChange={handleChange}
                            placeholder="Nhập mật khẩu"
                            autoComplete="current-password"
                            disabled={loading}
                        />

                    </div>


                    <button
                        type="submit"
                        disabled={loading}
                    >

                        {loading
                            ? "Đang đăng nhập..."
                            : "Đăng nhập"
                        }

                    </button>

                </form>


                <div className="register-link">

                    Chưa có tài khoản?

                    <Link to="/register">
                        {" "}Đăng ký
                    </Link>

                </div>

            </div>

        </div>
    );
}

export default Login;

