package com.project.salemanagement.Services;

import com.project.salemanagement.Repositories.CompanyRepo;
import com.project.salemanagement.Repositories.TaskRepo;
import com.project.salemanagement.Repositories.UserRepo;
import com.project.salemanagement.dtos.TaskDTO;
import com.project.salemanagement.models.Company;
import com.project.salemanagement.models.Status;
import com.project.salemanagement.models.Task;
import com.project.salemanagement.models.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;

@Service
@AllArgsConstructor
public class TaskService implements ITasksService {
    private final CompanyRepo companyRepo;
    private final UserRepo userRepo;
    private final TaskRepo taskRepo;

    @Override
    public Task createTask(TaskDTO taskDTO) {
        Company company = companyRepo.findById(taskDTO.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Cannot find Company with id:" + taskDTO.getCompanyId()));
        User user = userRepo.findById(taskDTO.getAssign())
                .orElseThrow(() -> new IllegalArgumentException("Cannot find User with id:" + taskDTO.getAssign()));

        Task task = Task.builder()
                .title(taskDTO.getTitle())
                .description(taskDTO.getDescription())
                .action(taskDTO.getAction())
                .company(company)
                .urgent(taskDTO.getUrgent())
                .startDate(taskDTO.getStartDate())
                .completedDate(taskDTO.getCompletedDate())
                .assignedUser(user)
                .build();
        return task;
    }

    @Override
    public Task getTask(long id) {
        Task task = taskRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find Task " ));
        return task;
    }

    @Override
    public Task updateTask(long id, TaskDTO taskDTO) {
        Company company = companyRepo.findById(taskDTO.getCompanyId())
                .orElseThrow(()->new InvalidParameterException("Cannot Found Company!"));
        User user = userRepo.findById(taskDTO.getAssign())
                .orElseThrow(()->new InvalidParameterException("Cannot Found User"));
        Task task = taskRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find Task "));
        task.setTitle(taskDTO.getTitle());
        task.setAction(taskDTO.getAction());
        task.setCompany(company);
        task.setUrgent(taskDTO.getUrgent());
        task.setStatus(Status.valueOf(taskDTO.getStatus().toUpperCase()));
        task.setAssignedUser(user);
        taskRepo.save(task);
        return task;
    }

    @Override
    public void deleteTask(long id) {

    }

    @Override
    public boolean existsByName(String name) {
        return false;
    }
}
