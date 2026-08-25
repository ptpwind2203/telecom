import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import {
    getProfile,
    logoutUser
} from "../services/userService";
import "../css/Profile.css";

function Profile() {

    const navigate = useNavigate();

    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    // ==========================================
    // LẤY THÔNG TIN USER
    // ==========================================

    useEffect(() => {

        const fetchProfile = async () => {

            try {

                const result = await getProfile();

                console.log("Profile response:", result);

                // API có thể trả:
                // result.data
                // hoặc trực tiếp object user
                const profileData = result?.data || result;

                setUser(profileData);

            } catch (err) {

                console.error("Profile error:", err);

                if (
                    err.response?.status === 401 ||
                    err.response?.status === 403
                ) {
                    navigate("/login");
                    return;
                }

                setError(
                    err.response?.data?.message ||
                    "Không thể lấy thông tin tài khoản."
                );

            } finally {

                setLoading(false);

            }
        };

        fetchProfile();

    }, [navigate]);

    // ==========================================
    // ĐĂNG XUẤT
    // ==========================================

    const handleLogout = async () => {

        try {

            await logoutUser();

            navigate("/login");

        } catch (err) {

            console.error("Logout error:", err);

            // Server logout lỗi vẫn chuyển về login
            navigate("/login");
        }
    };

    // ==========================================
    // FORMAT NGÀY
    // ==========================================

    const formatDate = (date) => {

        if (!date) {
            return "Chưa cập nhật";
        }

        try {

            const formattedDate = new Date(date);

            if (Number.isNaN(formattedDate.getTime())) {
                return date;
            }

            return formattedDate.toLocaleDateString("vi-VN");

        } catch (error) {

            return date;

        }
    };

    // ==========================================
    // LOADING
    // ==========================================

    if (loading) {

        return (
            <div className="profile-container">

                <div className="profile-card">

                    <p>
                        Đang tải thông tin tài khoản...
                    </p>

                </div>

            </div>
        );
    }

    // ==========================================
    // ERROR
    // ==========================================

    if (error) {

        return (
            <div className="profile-container">

                <div className="profile-card">

                    <div className="profile-error">
                        {error}
                    </div>

                    <button
                        onClick={() => navigate("/login")}
                    >
                        Đăng nhập lại
                    </button>

                </div>

            </div>
        );
    }

    // ==========================================
    // PROFILE
    // ==========================================

    return (
        <div className="profile-container">

            <div className="profile-card">

                {/* ==============================
                    HEADER
                ============================== */}

                <div className="profile-header">

                    <div className="avatar">

                        {user?.full_name
                            ?.charAt(0)
                            ?.toUpperCase()
                            || "U"
                        }

                    </div>

                    <h1>
                        Thông tin tài khoản
                    </h1>

                </div>

                {/* ==============================
                    THÔNG TIN USER
                ============================== */}

                <div className="profile-info">

                    {/* ID */}
                    <div className="profile-row">

                        <span className="profile-label">
                            ID
                        </span>

                        <span className="profile-value">
                            {user?.id ?? "Chưa có"}
                        </span>

                    </div>

                    {/* HỌ VÀ TÊN */}
                    <div className="profile-row">

                        <span className="profile-label">
                            Họ và tên
                        </span>

                        <span className="profile-value">
                            {user?.full_name || "Chưa cập nhật"}
                        </span>

                    </div>

                    {/* EMAIL */}
                    <div className="profile-row">

                        <span className="profile-label">
                            Email
                        </span>

                        <span className="profile-value">
                            {user?.email || "Chưa cập nhật"}
                        </span>

                    </div>

                    {/* SỐ ĐIỆN THOẠI */}
                    <div className="profile-row">

                        <span className="profile-label">
                            Số điện thoại
                        </span>

                        <span className="profile-value">
                            {user?.phone || "Chưa cập nhật"}
                        </span>

                    </div>

                    {/* ROLE */}
                    <div className="profile-row">

                        <span className="profile-label">
                            Role
                        </span>

                        <span className="profile-value">
                            {user?.role || "Chưa có"}
                        </span>

                    </div>

                    {/* TRẠNG THÁI */}
                    <div className="profile-row">

                        <span className="profile-label">
                            Trạng thái
                        </span>

                        <span className="profile-value">
                            {user?.status || "Chưa có"}
                        </span>

                    </div>

                    {/* NGÀY TẠO */}
                    <div className="profile-row">

                        <span className="profile-label">
                            Ngày tạo
                        </span>

                        <span className="profile-value">
                            {formatDate(user?.created_at)}
                        </span>

                    </div>

                    {/* NGÀY CẬP NHẬT */}
                    <div className="profile-row">

                        <span className="profile-label">
                            Ngày cập nhật
                        </span>

                        <span className="profile-value">
                            {formatDate(user?.update_at)}
                        </span>

                    </div>

                </div>

                {/* ==============================
                    ACTIONS
                ============================== */}

                <div className="profile-actions">

                    <button
                        className="back-button"
                        onClick={() => navigate("/")}
                    >
                        Trang chủ
                    </button>

                    <button
                        className="logout-button"
                        onClick={handleLogout}
                    >
                        Đăng xuất
                    </button>

                </div>

            </div>

        </div>
    );
}

export default Profile;
