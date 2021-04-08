package com.example.demo.uploads;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.constants.FileStatus;


@Repository
public interface UploadRepository extends JpaRepository<Upload, Integer>{

	Upload findFirstByStatusOrderByTimestamp(FileStatus inprogress);

}
