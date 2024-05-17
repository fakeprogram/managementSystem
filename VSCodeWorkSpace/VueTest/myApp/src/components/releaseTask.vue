<script setup lang="ts">
import { reactive, ref } from 'vue'
import type { ComponentSize, FormInstance, FormRules } from 'element-plus'
import type { FormProps } from 'element-plus'
import axios from 'axios';
import { ElMessage } from 'element-plus';
import router from '@/router/router';

interface RuleForm {
    taskName: string
    province: string,
    city: string,
    county: string,
    street: string,
    lastDetail: string,
    startTime: string,
    endTime: string,
    taskType: string,
    taskDetail: string,
}

var userData = ref([])
var receivers = ref([])

var newTaskId = ref(null)

const formSize = ref<ComponentSize>('default')
const ruleFormRef = ref<FormInstance>()
const ruleForm = reactive<RuleForm>({
    taskName: '',
    province: '',
    city: '',
    county: '',
    street: '',
    lastDetail: '',
    startTime: '',
    endTime: '',
    taskType: '',
    taskDetail: '',
})

const rules = reactive<FormRules<RuleForm>>({
    taskName: [
        { required: true, message: '请输入任务名称', trigger: 'blur' },
    ],
    province: [
        { required: true, message: '请输入省份', trigger: 'blur' },
    ],
    city: [
        { required: true, message: '请输入城市', trigger: 'blur' },
    ],
    county: [
        { required: true, message: '请输入区/县', trigger: 'blur' },
    ],
    street: [
        { required: true, message: '请输入街道/乡/镇', trigger: 'blur' },
    ],
    lastDetail: [{ required: false, }],
    startTime: [
        { required: true, message: '请输入开始时间', trigger: 'blur' },
    ],
    endTime: [
        { required: true, message: '请输入结束时间', trigger: 'blur' },
    ],
    taskType: [{ required: true, message: '请输入任务类型', trigger: 'blur' },],

    taskDetail: [{ required: true, message: '请输入任务详细信息', trigger: 'blur' },],
})
var submitButtonIsDisabled = ref(false)
var resetButtonIsDisabled = ref(false)

/**
 * 提交表单
 * @param formEl 
 */
const submitForm = async (formEl: FormInstance | undefined) => {
    if (!formEl) return
    await formEl.validate((valid, fields) => {
        if (valid) {
            console.log('submit!')
            addAddress()
        } else {
            console.log('error submit!', fields)
        }
    })
}

const resetForm = (formEl: FormInstance | undefined) => {
    if (!formEl) return
    formEl.resetFields()
}

/**
 * 添加地址
 */
function addAddress() {
    //地址数据
    var addressData = {
        "province": ruleForm.province,
        "city": ruleForm.city,
        "county": ruleForm.county,
        "street": ruleForm.street,
        "lastDetail": ruleForm.lastDetail,
    }

    axios({
        url: "/api/taskManagement/addAddress",
        method: "post",
        data: addressData,
        headers: {
            'Content-Type': 'application/json',
            "token": localStorage.getItem("token"),
        }
    })
        .then(function (response) {
            console.log(response);
            if (response.data.success === true) {
                //添加地址成功后添加任务
                releaseTask(response.data.data.addressId)
            } else {
                ElMessage.error(response.data.msg + "任务发布失败")
            }
        })
        .catch(function (error) {
            ElMessage.error("操作失败")
            console.log(error);
        });
}

/**
 * 发布任务
 */
function releaseTask(addressId) {
    //任务数据
    var taskData = {
        "taskName": ruleForm.taskName,
        "departmentId": JSON.parse(localStorage.getItem("user")).department_id,
        "addressId": addressId,
        "startTime": ruleForm.startTime,
        "endTime": ruleForm.endTime,
        "taskFile": "",
        "taskProgress": 0,
        "taskType": ruleForm.taskType,
        "taskStatus": 1,
        "taskDetail": ruleForm.taskDetail
    }

    axios({
        url: "/api/taskManagement/releaseTask",
        method: "post",
        data: taskData,
        params: {
            "user_id": JSON.parse(localStorage.getItem("user")).user_id
        },
        headers: {
            'Content-Type': 'application/json',
            "token": localStorage.getItem("token"),
        }
    })
        .then(function (response) {
            console.log(response);
            if (response.data.success === true) {
                ElMessage({
                    message: '发布成功',
                    type: 'success',
                })
                ElMessage({
                    message: '正在获取人员数据',
                    type: 'success',
                })
                newTaskId.value = response.data.newTaskId
                alert(newTaskId.value)
                submitButtonIsDisabled.value = true
                resetButtonIsDisabled.value = true
                // 获取人员数据
                getStaff()
            } else {
                ElMessage.error(response.data.msg)
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * 获取人员
 */
function getStaff() {
    axios({
        url: "/api/taskManagement/assignment/getStaff",
        method: "get",
        params: {
            "user_id": JSON.parse(localStorage.getItem("user")).user_id
        },
        headers: {
            'Content-Type': 'application/json',
            "token": localStorage.getItem("token"),
        }
    })
        .then(function (response) {
            console.log(response);
            if (response.data.success === true) {
                ElMessage({
                    message: '获取人员成功',
                    type: 'success',
                })
                userData.value = response.data.data;
            } else {
                ElMessage.error(response.data.msg)
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * 指派人员
 */
function doAssign() {
    if (receivers.value.length == 0) {
        ElMessage.error("请选择指派人员")
    }
    else if (newTaskId.value === null) {
        ElMessage.error("无法获取任务ID，请检查任务是否已经发布")
    }
    console.log("receivers", receivers.value)
    axios({
        url: "/api/taskManagement/assignment/doAssignment",
        method: "post",
        data: receivers.value,
        params: {
            "appointor": JSON.parse(localStorage.getItem("user")).user_id,
            "task_id": newTaskId.value
        },
        headers: {
            'Content-Type': 'application/json',
            "token": localStorage.getItem("token")
        }
    })
        .then(function (response) {
            console.log(response);
            if (response.data.success === true) {
                ElMessage({
                    message: '分派成功',
                    type: 'success',
                })
                newTaskId.value = null;
                router.push({
                    name: 'releasedByMyself'
                })
            } else {
                ElMessage.error(response.data.msg)
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * 删除任务
 */
const deletTask = () => {
    /*axios.post('/api/task/giveupPublish', {
        taskId: newTaskId.value
    })
        .then(function (response) {
            if (response.data.code === 200) {
                ElMessage({
                    message: '任务删除',
                    type: 'success',
                })
                newTaskId.value = null;
                router.push({name: 'releasedByMyself'})
            }
        })
        .catch(function (error) {
            console.log(error);
        });*/
}

const exit = () => {
    router.push({name: 'releasedByMyself'})
}
</script>

<template>
    <div class="main">
        <div class="task-form">
            <h2 style="text-align: center;">填写任务信息</h2>
            <el-form ref="ruleFormRef" style="max-width: 600px" :model="ruleForm" :rules="rules" label-width="auto"
                class="demo-ruleForm" :size="formSize" status-icon>
                <el-form-item label="任务名称" prop="taskName">
                    <el-input v-model="ruleForm.taskName" />
                </el-form-item>
                <el-form-item label="省" prop="province">
                    <el-input v-model="ruleForm.province" />
                </el-form-item>
                <el-form-item label="市" prop="city">
                    <el-input v-model="ruleForm.city" />
                </el-form-item>
                <el-form-item label="区/县" prop="county">
                    <el-input v-model="ruleForm.county" />
                </el-form-item>
                <el-form-item label="街道/乡/镇" prop="street">
                    <el-input v-model="ruleForm.street" />
                </el-form-item>
                <el-form-item label="详细地址" prop="lastDetail">
                    <el-input v-model="ruleForm.lastDetail" type="textarea" />
                </el-form-item>
                <el-form-item label="开始时间" required prop="startTime">
                    <el-date-picker v-model="ruleForm.startTime" type="datetime" placeholder="选择时间"
                        value-format="YYYY-MM-DD HH:mm:ss" />
                </el-form-item>

                <el-form-item label="结束时间" required prop="endTime">
                    <el-date-picker v-model="ruleForm.endTime" type="datetime" placeholder="选择时间"
                        value-format="YYYY-MM-DD HH:mm:ss" />
                </el-form-item>

                <el-form-item label="任务类型" prop="taskType">
                    <el-input v-model="ruleForm.taskType" />
                </el-form-item>

                <el-form-item label="任务详细信息" prop="taskDetail">
                    <el-input v-model="ruleForm.taskDetail" type="textarea" />
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="submitForm(ruleFormRef)" :disabled="submitButtonIsDisabled">
                        发布
                    </el-button>
                    <el-button @click="resetForm(ruleFormRef)" :disabled="resetButtonIsDisabled">重置</el-button>
                    <el-button @click="deletTask">删除任务</el-button>
                    <el-button @click="exit">退出</el-button>
                </el-form-item>
            </el-form>
        </div>
        <div class="task-assign" style="text-align: center;">
            <h2>任务发布后可进行指派</h2>
            <div style="text-align: center">
                <el-transfer v-model="receivers" filterable :left-default-checked="[]" :right-default-checked="[]"
                    :titles="['可选人员', '选中人员']" :props="{// 自定义数据映射属性
                        key: 'user_id',
                        label: 'user_name',
                    }" :button-texts="['移除', '添加']" :format="{// 自定义头部数据显示格式
                        noChecked: '${total}',
                        hasChecked: '${checked}/${total}',
                    }" :data="userData">
                    <template #right-footer>
                        <el-button class="transfer-footer" size="small" @click="doAssign">完成指派</el-button>
                    </template>
                </el-transfer>
            </div>
        </div>
    </div>
</template>

<style scoped>
.main {
    position: relative;
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: row;
}

.task-form {
    width: 50%;
    min-width: 50%;
    height: 100%;
    border-right: 2px solid rgb(148, 143, 143);
    overflow: auto;
    -ms-overflow-style: none;  /* IE and Edge */
    scrollbar-width: none;  /* Firefox */
}

/* 隐藏 Chrome、Safari 和 Opera 的滚动条 */
.task-form::-webkit-scrollbar {
  display: none;
}

.task-assign::-webkit-scrollbar {
  display: none;
}

.task-assign {
    width: 50%;
    min-width:600px;
    height: 100%;
    -ms-overflow-style: none;  /* IE and Edge */
    scrollbar-width: none;  /* Firefox */
}

/**
 * 覆盖element-plus样式
 */
.el-transfer {
    --el-transfer-panel-width: 190px;
}

.el-transfer-panel {
    background: var(--el-bg-color-overlay);
    box-sizing: border-box;
    display:inline-block;
    max-height: 100%;
    overflow: hidden;
    position: relative;
    text-align: left;
    vertical-align: middle;
    width: var(--el-transfer-panel-width);
}

/* .el-transfer__button {
    padding: 0;
} */

</style>