package com.example.managementSystem.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.example.managementSystem.entity.vo.UserWithDepartment;

/**
 * ;(UserWithDepartment)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2024-5-4
 */
@Mapper
public interface UserWithDepartmentMapper {
    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    UserWithDepartment queryById(Integer userId);

    /**
     * 统计总行数
     *
     * @param userwithdepartment 查询条件
     * @return 总行数
     */
    long count(UserWithDepartment userwithdepartment);
    /**
     * 新增数据
     *
     * @param userwithdepartment 实例对象
     * @return 影响行数
     */
    int insert(UserWithDepartment userwithdepartment);
    /**
     * 批量新增数据
     *
     * @param entities List<Userwithdepartment> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<UserWithDepartment> entities);
    /**
     * 批量新增或按主键更新数据
     *
     * @param entities List<Userwithdepartment> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<UserWithDepartment> entities);
    /**
     * 更新数据
     *
     * @param userwithdepartment 实例对象
     * @return 影响行数
     */
    int update(UserWithDepartment userwithdepartment);
    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 影响行数
     */
    int deleteById(Integer userId);
}
