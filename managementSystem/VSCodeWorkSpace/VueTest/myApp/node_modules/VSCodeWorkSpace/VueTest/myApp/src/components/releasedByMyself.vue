<script setup>
import { Menu as IconMenu, Message, Setting, Plus,User } from '@element-plus/icons-vue'
import { createRouter, createWebHistory, useRoute, useRouter } from "vue-router";
import axios from "axios";
import { ref, reactive, toRefs, onMounted, onBeforeMount } from "vue";

var taskData = ref([])

const router = useRouter()

onMounted(() => {
    getData()
})

function getData() {
    axios({
        url: "/api/taskManagement/getTaskReleasedByMe",
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
                taskData.value = response.data.data
                console.log(taskData.value);
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * 处理接收者数组
 * @param receivers 接收者数组
 */
function processReceivers(receivers) {
    if (receivers.length == 0) return ""
    var receivers_str = "|  "
    for (var i = 0; i < receivers.length; i++) {
        receivers_str += receivers[i].user_name + "  |  "
    }
    return receivers_str
}

const addNewTask = () => {
    router.push({ name: 'releaseTask' });
}
</script>

<template>
    <div class="create-new-task"  @click="addNewTask">
        <el-icon class="avatar-uploader-icon">
            <Plus />新建任务
        </el-icon>
    </div>
    <div style="width: 100%; height: 10%; margin-top: 20px;background-color: rgba(163, 171, 171,0.4);">
        <el-icon class="avatar-uploader-icon">
            <User />我发布的任务
        </el-icon>
    </div>
    <el-scrollbar>
        <el-table empty-text="暂无数据" :data="taskData" style="width: 100%">
            <el-table-column type="expand">
                <template #default="props">
                    <div m="7">
                        <p m="t-0 b-2">任务名称: {{ props.row.task.taskName }}</p><br>
                        <p m="t-0 b-2">任务详细信息: {{ props.row.task.taskDetail }}</p><br>
                        <p m="t-0 b-2">任务地址: {{ props.row.task.address_name }}</p><br>
                        <p m="t-0 b-2">开始时间: {{ props.row.task.startTime }}</p><br>
                        <p m="t-0 b-2">结束时间: {{ props.row.task.endTime }}</p><br>
                        <p m="t-0 b-2">任务类型: {{ props.row.task.taskType }}</p><br>
                        <p m="t-0 b-2">发布时间: {{ props.row.task.releaseTime }}</p><br>
                        <p m="t-0 b-2">任务状态: {{ props.row.task.taskStatus }}</p><br>
                        <p m="t-0 b-2">任务进度: {{ props.row.task.taskProgress }}%</p><br>
                        <p m="t-0 b-2">负责人员: {{ processReceivers(props.row.receivers) }}</p><br>
                    </div>
                </template>
            </el-table-column>
            <el-table-column prop="task.taskName" label="任务名称" width="300" />
            <el-table-column prop="task.releaseTime" label="发布时间" width="300" />
            <el-table-column prop="task.taskType" label="类型" width="120" />
            <el-table-column prop="task.city" label="地点" width="130" />
            <el-table-column prop="task.taskStatus" label="任务状态" width="130" />
            <!-- <el-table-column prop="taksId" fixed="right" label="操作" width="120">
                <template #default>
                    <el-button link type="primary" size="small" @click="handleClick">
                        详情
                    </el-button>
                </template>
            </el-table-column> -->
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
.create-new-task{
    position:relative;
    width: 100%; 
    height: 10%; 
    margin-top: 20px;
    background-color: rgba(163, 171, 171,0.4);
    cursor: pointer;
}

.create-new-task:hover{
    background-color: rgb(163, 171, 171);
}

</style>