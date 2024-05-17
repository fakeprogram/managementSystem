<script setup>
import { Menu as IconMenu, Message, Setting, List } from '@element-plus/icons-vue'
import { createRouter, createWebHistory, useRoute, useRouter } from "vue-router";
import axios from "axios";
import { ref, reactive, toRefs, onMounted, onBeforeMount } from "vue";
import { ElMessage } from 'element-plus';
import router from '@/router/router';
var unSignForedTaskData = ref([])//未签收任务
var signForedTaskData = ref([])//已签收任务

/**
 * 选中数据
 */
const multipleSelection = ref([])
const handleSelectionChange = (val) => {
    multipleSelection.value = val
    console.log(multipleSelection.value)
}
onMounted(() => {
    getData()
})

/*
* 获取数据
*/
function getData() {
    //**
    /**
     * 获取未签收任务
     */
    axios({
        url: "/api/personalTaskManagement/getMyNewTask",
        method: "post",
        params: {
            "user_id": JSON.parse(localStorage.getItem("user")).user_id
        },
        headers: {
            'Content-Type': 'application/json',
            "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwYXNzd29yZCI6IjEyMzQ1NiIsInVzZXJfaWQiOjQsImV4cCI6MTcxNDgwOTI2MH0.qF9PLDg3wm8AwepwQskVu3SYooYFicqpWTedWX2mBBE"
        }
    })
        .then(function (response) {
            if (response.data.success == true) {
                // console.log(response.data.data);
                unSignForedTaskData.value = response.data.data
                console.log(unSignForedTaskData.value);
            }
        })
        .catch(function (error) {
            console.log(error);
        });

    
    /*
    * 获取已签收任务
    */
    axios({
        url: "/api/personalTaskManagement/getMySignForedTask",
        method: "post",
        params: {
            "user_id": JSON.parse(localStorage.getItem("user")).user_id
        },
        headers: {
            'Content-Type': 'application/json',
            "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwYXNzd29yZCI6IjEyMzQ1NiIsInVzZXJfaWQiOjQsImV4cCI6MTcxNDgwOTI2MH0.qF9PLDg3wm8AwepwQskVu3SYooYFicqpWTedWX2mBBE"
        }
    })
        .then(function (response) {
            if (response.data.success == true) {
                // console.log(response.data.data);
                signForedTaskData.value = response.data.data.allTask
                console.log(signForedTaskData.value);
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * 签收选中任务
 */
function signForSelection() {
    if (multipleSelection.value.length == 0) {
        ElMessage({
            message: '请选择要签收的任务',
            type: 'warning',
        })
    }
    else {
        let assignmentIdList = []
        for (let i = 0; i < multipleSelection.value.length; i++) {
            assignmentIdList.push(multipleSelection.value[i].assignmentId)
        }
        console.log(assignmentIdList);

        axios({
            url: "/api/personalTaskManagement/signFor",
            method: "post",
            data: assignmentIdList,
            headers: {
                'Content-Type': 'application/json',
                "token": localStorage.getItem("token"),
            }
        })
            .then(function (response) {
                console.log(response);
                if (response.data.success === true) {
                    ElMessage({
                        message: response.data.msg,
                        type: 'success',
                    })
                    router.go(0)
                } else {
                    ElMessage.error(response.data.msg)
                }
            })
            .catch(function (error) {
                ElMessage.error("操作失败")
                console.log(error);
            });
    }


}
</script>

<template>
    <div style="width: 100%; height: 10%; margin-top: 20px;background-color: rgba(163, 171, 171,0.4);">
        <el-icon class="avatar-uploader-icon">
            <List />待签收任务
        </el-icon>
    </div>
    <div style="margin-top: 20px">
        <el-button @click="signForSelection">
            签收选中任务
        </el-button>
    </div>
    <el-scrollbar>
        <el-table empty-text="暂无数据" :data="unSignForedTaskData" @selection-change="handleSelectionChange" style="width: 100%">
            <el-table-column type="expand">
                <template #default="props">
                    <div m="7" style="padding:10px 60px ;">
                        <p m="t-0 b-2">任务名称: {{ props.row.taskName }}</p><br>
                        <p m="t-0 b-2">任务详细信息: {{ props.row.taskDetail }}</p><br>
                        <p m="t-0 b-2">任务地址: {{ props.row.address }}</p><br>
                        <p m="t-0 b-2">开始时间: {{ props.row.startTime }}</p><br>
                        <p m="t-0 b-2">结束时间: {{ props.row.endTime }}</p><br>
                        <p m="t-0 b-2">任务类型: {{ props.row.taskType }}</p><br>
                        <p m="t-0 b-2">分派人: {{ props.row.appointor }}</p><br>
                        <p m="t-0 b-2">分派时间: {{ props.row.assignmentDate }}</p><br>
                        <p m="t-0 b-2">部门: {{ props.row.departmentName }}%</p><br>
                    </div>
                </template>
            </el-table-column>
            <el-table-column type="selection" width="55" />
            <el-table-column prop="taskName" label="任务名称" width="300" />
            <el-table-column prop="assignmentDate" label="分派时间" width="300" />
            <el-table-column prop="startTime" label="开始时间" width="300" />
            <el-table-column prop="taskType" label="任务类型" width="120" />
            <el-table-column prop="appointor" label="分派人" width="130" />
        </el-table>
    </el-scrollbar>
    <div style="width: 100%; height: 10%; margin-top: 20px;background-color: rgba(163, 171, 171,0.4);">
        <el-icon class="avatar-uploader-icon">
            <List />已签收任务
        </el-icon>
    </div>
    <el-scrollbar>
        <el-table empty-text="暂无数据" :data="signForedTaskData" style="width: 100%">
            <el-table-column type="expand">
                <template #default="props">
                    <div m="7" style="padding:10px 60px ;">
                        <p m="t-0 b-2">任务名称: {{ props.row.taskName }}</p><br>
                        <p m="t-0 b-2">任务详细信息: {{ props.row.taskDetail }}</p><br>
                        <p m="t-0 b-2">任务地址: {{ props.row.address }}</p><br>
                        <p m="t-0 b-2">开始时间: {{ props.row.startTime }}</p><br>
                        <p m="t-0 b-2">结束时间: {{ props.row.endTime }}</p><br>
                        <p m="t-0 b-2">任务类型: {{ props.row.taskType }}</p><br>
                        <p m="t-0 b-2">分派人: {{ props.row.appointor }}</p><br>
                        <p m="t-0 b-2">分派时间: {{ props.row.assignmentDate }}</p><br>
                        <p m="t-0 b-2">部门: {{ props.row.departmentName }}%</p><br>
                    </div>
                </template>
            </el-table-column>
            <el-table-column prop="taskName" label="任务名称" width="300" />
            <el-table-column prop="assignmentDate" label="分派时间" width="300" />
            <el-table-column prop="startTime" label="开始时间" width="300" />
            <el-table-column prop="taskType" label="任务类型" width="120" />
            <el-table-column prop="appointor" label="分派人" width="130" />
        </el-table>
        
    </el-scrollbar>
</template>

<style scoped>
.el-icon.avatar-uploader-icon {
    font-size: 28px;
    color: #1a1b1c;
    width: 100%;
    height: 100%;
    text-align: center;
}

.el-scrollbar{
    overflow: auto;
    height:500px;
}
</style>