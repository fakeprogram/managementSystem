<script setup>
import { Menu as IconMenu, Message, Setting, List } from '@element-plus/icons-vue'
import { createRouter, createWebHistory, useRoute, useRouter } from "vue-router";
import axios from "axios";
import { ref, reactive, toRefs, onMounted, onBeforeMount } from "vue";
import { ElMessage, ElMessageBox } from 'element-plus';
import router from '@/router/router';


var myOnGoingTask = ref([])
var reportForm = ref({
    taskName: '',
    reporter: JSON.parse(localStorage.getItem("user")).user_name,
    taskprogress:null,
    content: '',
})
const tableRef = ref(null)
const reportFormRf = ref(null)
const rules = reactive({
    taskName: [
        { required: true, message: '请选择要审核的申请', trigger: 'blur' }
    ],
    reporter: [
        { required: true, message: '汇报人错误', trigger: 'blur' }
    ],
    taskprogress: [
        { required: true, message: '请选填写任务进度百分比', trigger: 'blur' }
    ],
    content: [
        { required: true, message: '请输入汇报内容', trigger: 'blur' }
    ],
})

/**
 * 选中数据
 */
const multipleSelection = ref([])
const handleSelectionChange = (val) => {
    multipleSelection.value = val
    if (val.length > 0) {
        reportForm.value.taskName = val[0].taskName
    }
    else {
        reportForm.value.taskName = ''
    }
    console.log(multipleSelection.value)
}
onMounted(() => {
    getData()
})

/*
* 获取数据
*/
function getData() {
    axios({
        url: "/api/personalTaskManagement/myOnGoingTask",
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
                console.log(response.data.data);
                myOnGoingTask.value = response.data.data
                ElMessage({
                    message: response.data.msg,
                    type: 'success',
                })
            }
            else {
                ElMessage({
                    message: response.data.msg,
                    type: 'error',
                })
            }
        })
        .catch(function (error) {
            ElMessage({
                message: "获取数据失败",
                type: 'error',
            })
            console.log(error);
        });
}

/**
 * 提交汇报
 */
function submitAudit() {
    if (multipleSelection.value.length == 0) {
        ElMessage({
            message: "请选择要汇报的任务",
            type: 'warning',
        })
        return;
    }
    else {
        ElMessageBox.confirm('确定要提交吗？', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
        })
            .then(() => {
                var report = {
                    "taskId": multipleSelection.value[0].taskId,
                    "repoter": JSON.parse(localStorage.getItem("user")).user_id,
                    "taskprogress": reportForm.value.taskprogress,
                    "content": reportForm.value.content,
                }
                console.log(report)
                axios({
                    url: "/api/personalTaskManagement/taskReport",
                    method: "post",
                    data: report,
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
            })
    }
}

/**
 * 提交表单
 */
const submitForm = async (formEl) => {
    if (!formEl) return
    if(reportForm.value.taskprogress>100 || reportForm.value.taskprogress<0){
        ElMessage({
            message: "任务进度百分比必须在0-100之间",
            type: 'warning',
        })
        return;
    }
    await formEl.validate((valid, fields) => {
        if (valid) {
            console.log('submit!')
            submitAudit()
        } else {
            console.log('error submit!', fields)
        }
    })
}


/**
 * 重置表单
 * @param formEl 
 */
function resetForm(formEl) {
    // selectedTaskId.value = null
    if (!formEl) return
    formEl.resetFields()
}

/**
 *  取消
 */
function cancelAudit() {
    multipleSelection.value = []
    // console.log(tableRef.value)
    tableRef.value.clearSelection()
    reportForm.value.taskName = ''
    resetForm(reportFormRf.value)
}

/**
 * 是否可进行选择
 */

function isSelectable() {
    if (multipleSelection.value.length != 0) {
        return false
    } else {
        return true
    }
}
</script>

<template>
    <el-container>
        <el-main>
            <div style="width: 100%; height: 10%; margin-top: 20px;background-color: rgba(163, 171, 171,0.4);">
                <el-icon class="avatar-uploader-icon">
                    <List />我的任务
                </el-icon>
            </div>
            <el-scrollbar>
                <el-table empty-text="暂无数据" :data="myOnGoingTask" ref="tableRef"
                    @selection-change="handleSelectionChange" style="width: 100%">
                    <el-table-column type="expand">
                        <template #default="props">
                            <div m="7">
                                <p m="t-0 b-2">任务名称: {{ props.row.taskName }}</p><br>
                                <p m="t-0 b-2">任务详细信息: {{ props.row.taskDetail }}</p><br>
                                <p m="t-0 b-2">任务地址: {{ props.row.address }}</p><br>
                                <p m="t-0 b-2">开始时间: {{ props.row.startTime }}</p><br>
                                <p m="t-0 b-2">结束时间: {{ props.row.endTime }}</p><br>
                                <p m="t-0 b-2">任务类型: {{ props.row.taskType }}</p><br>
                                <p m="t-0 b-2">任务进度: {{ props.row.taskProgress }}%</p><br>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column type="selection" width="55" :selectable="isSelectable"/>
                    <el-table-column prop="taskName" label="任务名称" width="300" />
                    <el-table-column prop="departmentName" label="部门" width="150" />
                    <el-table-column prop="taskType" label="类型" width="120" />
                    <el-table-column prop="address" label="地点" width="130" />
                    <el-table-column prop="taskProgress" label="进度/%" width="130" />
                </el-table>
            </el-scrollbar>
        </el-main>
        <el-aside>
            <div style="width: 100%; height: 5%; margin-top:30%;background-color: rgba(163, 171, 171,0.4);">
                <el-icon class="avatar-uploader-icon">
                    <List />填写任务汇报信息
                </el-icon>
            </div>
            <div class="audit-form-container">
                <el-form ref="reportFormRf" label-position="right" style="margin: 20px 0 0 0;" :model="reportForm"
                    :rules="rules">
                    <el-form-item label="任务名称">
                        <el-input v-model="reportForm.taskName" type="textarea" disabled />
                    </el-form-item>
                    <el-form-item label="汇报人" prop="reporter">
                        <el-input v-model="reportForm.reporter" disabled />
                    </el-form-item>
                    <el-form-item label="任务进度" prop="taskprogress">
                        <el-input v-model="reportForm.taskprogress" type="number" max='100'  min='0'/>
                    </el-form-item>
                    <el-form-item label="汇报内容" prop="content">
                        <el-input v-model="reportForm.content" type="textarea" />
                    </el-form-item>
                </el-form>
                <el-form-item>
                    <el-button type="primary" @click="submitForm(reportFormRf)" :disabled="submitButtonIsDisabled">
                        提交
                    </el-button>
                    <el-button @click="resetForm(reportFormRf)" :disabled="resetButtonIsDisabled">重置</el-button>
                    <el-button @click="cancelAudit">取消</el-button>
                </el-form-item>
            </div>
        </el-aside>
    </el-container>
</template>


<style scoped>
.el-container {
    width: 100%;
    height: 100%;
}

.el-container .el-main {
    padding: 0;
    width: 70%;
    border-right: 3px solid rgba(58, 57, 57, 0.5);
    -ms-overflow-style: none;
    /* IE and Edge */
    scrollbar-width: none;
    /* Firefox */
}

/* 隐藏 Chrome、Safari 和 Opera 的滚动条 */
.el-container .el-main::-webkit-scrollbar {
    display: none;
}

.el-container .el-aside {
    width: 30%;
}

.el-icon.avatar-uploader-icon {
    font-size: 28px;
    color: #1a1b1c;
    width: 100%;
    height: 100%;
    text-align: center;
}
</style>