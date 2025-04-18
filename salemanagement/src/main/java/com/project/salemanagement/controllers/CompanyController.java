package com.project.salemanagement.controllers;

import com.project.salemanagement.Services.Imp.CompanyService;
import com.project.salemanagement.dtos.CompanyDTO;
import com.project.salemanagement.models.Company;
import com.project.salemanagement.response.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("salemanagement/v1/company")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping("/admin")
    public ResponseEntity<?> getAllCompany(
            int pageNo,
            int pageSize,
            String... sortBy) {
        PageResponse<?> companyPage = companyService.getAllCompany(pageNo,
                pageSize,
                sortBy);
        //List<CompanyResponse> companyResponseList = CompanyResponse.fromListCompany(companyPage.getItems());
        return ResponseEntity.ok().body(companyPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCompany(@PathVariable Long id) {
        try {
            Company company = companyService.getCompany(id);
            return ResponseEntity.ok().body(company);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/assignedPerson")
    public ResponseEntity<?> getCompanyByAssignedUser(@RequestParam String email) {
        try {

            List<Company> companyList = companyService.getCompanyByAssignEmail(email);
            return ResponseEntity.ok().body(companyList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createCompany(@Valid @RequestBody CompanyDTO companyDTO,
                                           BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> Error = result.getFieldErrors()
                        .stream()
                        .map(fieldError -> fieldError.getDefaultMessage())
                        .toList();
                return ResponseEntity.badRequest().body(Error);
            }
            Company company = companyService.createCompany(companyDTO);
            return ResponseEntity.ok().body(company);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCompany(@PathVariable Long id, @Valid @RequestBody CompanyDTO companyDTO,
                                           BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> Error = result.getFieldErrors()
                        .stream()
                        .map(fieldError -> fieldError.getDefaultMessage())
                        .toList();
                return ResponseEntity.badRequest().body(Error);
            }
            Company company = companyService.updateCompany(id, companyDTO);
            return ResponseEntity.ok().body(company);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
