$(document).ready(function () {

    // --- 1. XỬ LÝ KHI TRANG PROFILE LOAD ---
    // Kiểm tra nếu đang ở trang profile thì mới gọi API lấy thông tin
    if (window.location.pathname.includes("profile")) {
        $.ajax({
            type: "GET",
            url: "/users/me",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            beforeSend: function (xhr) {
                if (localStorage.token) {
                    // Sửa lỗi: Truyền giá trị thực từ localStorage thay vì chuỗi literal
                    xhr.setRequestHeader("Authorization", "Bearer " + localStorage.token);
                }
            },
            success: function (data) {
                // Hiển thị tên đầy đủ
                $('#profile').html("<strong>Full Name:</strong> " + data.fullName + "<br><strong>Email:</strong> " + data.email);

                // Hiển thị ảnh nếu có
                if (data.images) {
                    $('#userImage').attr("src", data.images).show();
                }
            },
            error: function (e) {
                console.error("ERROR: ", e);
                alert("Phiên đăng nhập hết hạn hoặc bạn chưa đăng nhập.");
                window.location.href = "/login";
            }
        });
    }

    // --- 2. HÀM ĐĂNG NHẬP ---
    $("#btnLogin").click(function (event) {
        event.preventDefault(); // Ngăn chặn form submit mặc định

        var email = $('#email').val();
        var password = $('#password').val();

        var loginData = JSON.stringify({
            email: email,
            password: password
        });

        $.ajax({
            type: "POST",
            url: "/auth/login",
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            data: loginData,
            success: function (data) {
                // Lưu token vào localStorage
                localStorage.token = data.token;
                alert("Đăng nhập thành công!");
                // Chuyển hướng sang trang profile
                window.location.href = "/user/profile";
            },
            error: function (e) {
                alert("Đăng nhập thất bại: Sai email hoặc mật khẩu.");
                console.log("ERROR: ", e);
            }
        });
    });

    // --- 3. HÀM ĐĂNG XUẤT ---
    $('#btnLogout').click(function () {
        localStorage.clear(); // Xóa sạch token
        window.location.href = "/login";
    });

});