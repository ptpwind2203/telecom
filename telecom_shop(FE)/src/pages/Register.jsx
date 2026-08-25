import { useState } from "react";
import { registerUser } from "../services/userService";
import "../css/Register.css";

function Register() {
    const [formData, setFormData] = useState({
        full_name: "",
        email: "",
        phone: "",
        password: "",
        confirmPassword: ""
    });

    const [message, setMessage] = useState("");
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);

    const handleChange = (e) => {
        const { name, value } = e.target;

        setFormData((prev) => ({
            ...prev,
            [name]: value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        setMessage("");
        setError("");

        // Kiểm tra dữ liệu bắt buộc
        if (
            !formData.full_name.trim() ||
            !formData.email.trim() ||
            !formData.phone.trim() ||
            !formData.password ||
            !formData.confirmPassword
        ) {
            setError("Vui lòng nhập đầy đủ thông tin.");
            return;
        }

        // Kiểm tra email
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        if (!emailRegex.test(formData.email)) {
            setError("Email không hợp lệ.");
            return;
        }

        // Kiểm tra mật khẩu
        if (formData.password.length < 6) {
            setError("Mật khẩu phải có ít nhất 6 ký tự.");
            return;
        }

        // Kiểm tra xác nhận mật khẩu
        if (formData.password !== formData.confirmPassword) {
            setError("Mật khẩu xác nhận không khớp.");
            return;
        }

        try {
            setLoading(true);

            // Chỉ gửi những field backend cần
            const userData = {
                full_name: formData.full_name.trim(),
                email: formData.email.trim(),
                phone: formData.phone.trim(),
                password: formData.password
            };

            console.log("Dữ liệu gửi lên backend:", userData);

            const result = await registerUser(userData);

            setMessage(
                result?.message || "Đăng ký tài khoản thành công!"
            );

            // Reset form sau khi đăng ký thành công
            setFormData({
                full_name: "",
                email: "",
                phone: "",
                password: "",
                confirmPassword: ""
            });
        } catch (err) {
            console.error("Register error:", err);

            if (err.response) {
                setError(
                    err.response.data?.message ||
                    err.response.data?.error ||
                    "Đăng ký thất bại."
                );
            } else if (err.request) {
                setError(
                    "Không thể kết nối đến server. Vui lòng kiểm tra backend."
                );
            } else {
                setError("Có lỗi xảy ra khi đăng ký.");
            }
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="register-container">
            <div className="register-card">
                <h1>Đăng ký tài khoản</h1>

                <p className="subtitle">
                    Tạo tài khoản mới để bắt đầu
                </p>

                {message && (
                    <div className="success-message">
                        {message}
                    </div>
                )}

                {error && (
                    <div className="error-message">
                        {error}
                    </div>
                )}

                <form onSubmit={handleSubmit}>
                    {/* Họ và tên */}
                    <div className="form-group">
                        <label htmlFor="full_name">
                            Họ và tên
                        </label>

                        <input
                            id="full_name"
                            type="text"
                            name="full_name"
                            value={formData.full_name}
                            onChange={handleChange}
                            placeholder="Nhập họ và tên"
                            disabled={loading}
                        />
                    </div>

                    {/* Email */}
                    <div className="form-group">
                        <label htmlFor="email">
                            Email
                        </label>

                        <input
                            id="email"
                            type="email"
                            name="email"
                            value={formData.email}
                            onChange={handleChange}
                            placeholder="example@gmail.com"
                            disabled={loading}
                        />
                    </div>

                    {/* Số điện thoại */}
                    <div className="form-group">
                        <label htmlFor="phone">
                            Số điện thoại
                        </label>

                        <input
                            id="phone"
                            type="tel"
                            name="phone"
                            value={formData.phone}
                            onChange={handleChange}
                            placeholder="Nhập số điện thoại"
                            disabled={loading}
                        />
                    </div>

                    {/* Mật khẩu */}
                    <div className="form-group">
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
                            disabled={loading}
                        />
                    </div>

                    {/* Xác nhận mật khẩu */}
                    <div className="form-group">
                        <label htmlFor="confirmPassword">
                            Xác nhận mật khẩu
                        </label>

                        <input
                            id="confirmPassword"
                            type="password"
                            name="confirmPassword"
                            value={formData.confirmPassword}
                            onChange={handleChange}
                            placeholder="Nhập lại mật khẩu"
                            disabled={loading}
                        />
                    </div>

                    {/* Button */}
                    <button
                        type="submit"
                        disabled={loading}
                    >
                        {loading
                            ? "Đang đăng ký..."
                            : "Đăng ký"}
                    </button>
                </form>

                <div className="login-link">
                    Đã có tài khoản?
                    <a href="/login"> Đăng nhập</a>
                </div>
            </div>
        </div>
    );
}

export default Register;
