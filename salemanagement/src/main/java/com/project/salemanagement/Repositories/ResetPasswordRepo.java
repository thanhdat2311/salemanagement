package com.project.salemanagement.Repositories;

import com.project.salemanagement.models.ResetPassword;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ResetPasswordRepo  extends JpaRepository<ResetPassword,Long> {
    ResetPassword findByOtpCode(String otpCode);
}
