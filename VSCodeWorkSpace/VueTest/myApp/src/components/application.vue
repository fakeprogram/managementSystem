<script setup lang="ts">
import { reactive, ref, onMounted } from 'vue'
import type { ComponentSize, FormInstance, FormRules } from 'element-plus'
import type { FormProps } from 'element-plus'
import axios from 'axios';
import { ElMessage } from 'element-plus';
import router from '@/router/router';

interface RuleForm {
    taskName: string
    address: string
    userName: string
    departmentName: string
    departureTime: string
    returnTime: string
    vehicle: string
    totalCost: number
    transportationExpense: number
    hotelExpense: number
    cateringExpenses: number
    otherExpenses: number
    days: number
    /**
     * 审核信息
     */
    auditorName: string
    auditTime: string
    isPass: string
    opinions: string
}

onMounted(() => {
    ElMessage({
        message: "正在获取任务数据",
        type: "success"
    })
    getMySignForedTask()
    getMyApplication()
})

var taskData = ref([])//任务数据
var myApplication = ref([])//申请记录
var selectedTaskId = ref(null)//选中任务ID
var selectedApplyId = ref(null)//选中申请ID

/**
 * 设置为选中项数据
 * @param task 
 */
const setSelectedTask = (task) => {
    selectedApplyId.value = null
    resetForm(ruleFormRef.value)
    ruleForm.taskName = task.taskName;
    ruleForm.address = task.address
}

/**
 * 设置选中申请记录
 * @param application 
 */
const setSelectedApplication = (application) => {
    selectedTaskId.value = null;
    ruleForm.taskName=application.taskName
    ruleForm.userName=application.userName
    ruleForm.departmentName=application.departmentName
    ruleForm.address=application.address
    ruleForm.vehicle=application.vehicle
    ruleForm.departureTime=application.departureTime
    ruleForm.returnTime=application.returnTime
    ruleForm.days=application.days
    ruleForm.totalCost=application.totalCost
    ruleForm.transportationExpense=application.transportationExpense
    ruleForm.hotelExpense=application.hotelExpense
    ruleForm.cateringExpenses=application.cateringExpenses
    ruleForm.otherExpenses=application.otherExpenses
    ruleForm.auditorName=application.auditorName
    ruleForm.auditTime=application.auditTime
    ruleForm.isPass=(application.applyStatus==0? "未审核":(application.isPass ==0 ? '不通过':'通过'))
    ruleForm.opinions=application.opinions
}

const formSize = ref<ComponentSize>('default')
const ruleFormRef = ref<FormInstance>()
var ruleForm = reactive<RuleForm>({
    taskName: '',
    address: '',
    userName: JSON.parse(localStorage.getItem("user")).user_name,
    departmentName: JSON.parse(localStorage.getItem("user")).departmentName,
    departureTime: '',
    returnTime: '',
    vehicle: '',
    totalCost: null,
    transportationExpense: null,
    hotelExpense: null,
    cateringExpenses: null,
    otherExpenses: null,
    days: null,
    /**
     * 审核信息
     */
    auditorName: "",
    opinions: "",
    auditTime: "",
    isPass: ''
})

/**
 * 数值检测
 */
const checkDays = (rule: any, value: any, callback: any) => {
    if (!value) {
        return callback(new Error('请输入数值'))
    }
    if (!Number.isInteger(value)) {
        callback(new Error('请输入数值'))
    } else {
        if (value < 0) {
            callback(new Error('数值不能为负数'))
        } else {
            callback()
        }
    }
}

const rules = reactive<FormRules<RuleForm>>({
    taskName: [
        { required: true, message: '请选择右侧任务名称', trigger: 'blur' }
    ],
    userName: [
        { required: true, message: '名称错误', trigger: 'blur' },
    ],
    address: [
        { required: true, message: '地址错误', trigger: 'blur' },
    ],
    departmentName: [
        { required: true, message: '部门错误', trigger: 'blur' },
    ],
    departureTime: [
        { required: true, message: '请输入出发时间', trigger: 'blur' },
    ],
    returnTime: [
        { required: true, message: '请输入返回时间', trigger: 'blur' },
    ],
    days: [
        { required: true, message: '请输入出差天数', trigger: 'blur' },
    ],
    vehicle: [{ required: true, message: '请输入交通工具', trigger: 'blur' }],
    totalCost: [
        { required: true, message: '请输入申请总费用', trigger: 'blur' },
    ],
    transportationExpense: [
        { required: true, message: '请输入预计交通费', trigger: 'blur' },
    ],
    hotelExpense: [{ required: true, message: '请输入预计住宿费', trigger: 'blur' },],

    cateringExpenses: [{ required: true, message: '请输入预计餐饮费', trigger: 'blur' },],
    otherExpenses: [{ required: true, message: '请输入预计其他费用', trigger: 'blur' },],
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
            submitApplication()
        } else {
            console.log('error submit!', fields)
        }
    })
}

/**
 * 重置表单
 * @param formEl 
 */
function resetForm(formEl: FormInstance | undefined) {
    // selectedTaskId.value = null
    if (!formEl) return
    formEl.resetFields()
}

/**
 * 获取任务列表
 */

function getMySignForedTask() {
    axios({
        url: "/api/personalTaskManagement/getMySignForedTask",
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
                    message: '获取任务数据成功',
                    type: 'success',
                })
                taskData.value = response.data.data.taskWithoutApplication
            } else {
                ElMessage.error(response.data.msg)
            }
        })
        .catch(function (error) {
            ElMessage.error("数据请求失败")
            console.log(error);
        });
}

/**
 * 提交申请
 */
function submitApplication() {
    let applyData = {
        'taskId': selectedTaskId.value,
        'userId': JSON.parse(localStorage.getItem("user")).user_id,
        'taskName': ruleForm.taskName,
        'address': ruleForm.address,
        'userName': ruleForm.userName,
        'departmentName': ruleForm.departmentName,
        'departureTime': ruleForm.departureTime,
        'returnTime': ruleForm.returnTime,
        'applyStatus': 0,
        'vehicle': ruleForm.vehicle,
        'totalCost': ruleForm.totalCost,
        'transportationExpense': ruleForm.transportationExpense,
        'hotelExpense': ruleForm.hotelExpense,
        'cateringExpenses': ruleForm.cateringExpenses,
        'otherExpenses': ruleForm.otherExpenses,
        'days': ruleForm.days
    }
    axios({
        url: "/api/personalTaskManagement/apply",
        method: "post",
        data: applyData,
        headers: {
            'Content-Type': 'application/json',
            "token": localStorage.getItem("token"),
        }
    })
        .then(function (response) {
            console.log(response);
            if (response.data.success === true) {
                ElMessage({
                    message: '提交成功',
                    type: 'success',
                })
                resetForm(ruleFormRef.value)
                router.go(0)
            } else {
                ElMessage.error(response.data.msg)
            }
        })
        .catch(function (error) {
            ElMessage.error("提交失败")
            console.log(error);
        });
}

/**
 *  获取我的申请记录
 */

function getMyApplication() {
    axios({
        url: "/api/personalTaskManagement/getMyApplication",
        method: "post",
        params: { "user_id": JSON.parse(localStorage.getItem("user")).user_id },
        headers: {
            'Content-Type': 'application/json',
            "token": localStorage.getItem("token"),
        }
    })
        .then(function (response) {
            console.log(response);
            if (response.data.success === true) {
                ElMessage({
                    message: '获取申请记录成功',
                    type: 'success',
                })
                myApplication.value = response.data.data
            } else {
                ElMessage.error(response.data.msg)
            }
        })
        .catch(function (error) {
            ElMessage.error("提交失败")
            console.log(error);
        });
}

/**
 * 退出
 */
const exit = () => {
    router.push({ name: 'myInfo' })
}

</script>

<template>
    <div class="main">
        <div class="apply-form">
            <h2 style="text-align: center;">{{ selectedApplyId==null? '填写申请信息':'我的申请' }}</h2>
            <el-form ref="ruleFormRef" style="max-width: 750px;min-width: 700px;" :model="ruleForm" :rules="rules"
                label-width="auto" class="demo-ruleForm" :size="formSize" status-icon>
                <el-form-item label="任务名称" prop="taskName">
                    <el-input v-model="ruleForm.taskName" placeholder="选择任务名称后自动填充" disabled />
                </el-form-item>
                <el-form-item label="申请人" prop="userName">
                    <el-input v-model="ruleForm.userName" disabled />
                </el-form-item>
                <el-form-item label="部门" prop="departmentName">
                    <el-input v-model="ruleForm.departmentName" disabled />
                </el-form-item>
                <el-form-item label="出差地址" prop="address">
                    <el-input v-model="ruleForm.address" placeholder="选择任务名称后自动填充" disabled />
                </el-form-item>
                <el-form-item label="交通工具" prop="vehicle">
                    <el-input v-model="ruleForm.vehicle" :disabled="selectedApplyId!=null"/>
                </el-form-item>
                <el-form-item label="出发时间" required prop="departureTime">
                    <el-date-picker v-model="ruleForm.departureTime" type="datetime" placeholder="选择时间"
                        value-format="YYYY-MM-DD HH:mm:ss" :disabled="selectedApplyId!=null"/>
                </el-form-item>

                <el-form-item label="返回时间" required prop="returnTime">
                    <el-date-picker v-model="ruleForm.returnTime" type="datetime" placeholder="选择时间"
                        value-format="YYYY-MM-DD HH:mm:ss" :disabled="selectedApplyId!=null"/>
                </el-form-item>

                <el-form-item label="出差天数" prop="days">
                    <el-input v-model="ruleForm.days" :disabled="selectedApplyId!=null"/>
                </el-form-item>

                <el-form-item label="申请总费用" prop="totalCost">
                    <el-input v-model="ruleForm.totalCost" :disabled="selectedApplyId!=null"/>
                </el-form-item>

                <el-form-item label="预计交通费" prop="transportationExpense">
                    <el-input v-model="ruleForm.transportationExpense" :disabled="selectedApplyId!=null"/>
                </el-form-item>
                <el-form-item label="预计住宿费" prop="hotelExpense">
                    <el-input v-model="ruleForm.hotelExpense" :disabled="selectedApplyId!=null"/>
                </el-form-item>
                <el-form-item label="预计餐饮费" prop="cateringExpenses">
                    <el-input v-model="ruleForm.cateringExpenses" :disabled="selectedApplyId!=null"/>
                </el-form-item>
                <el-form-item label="预计其他费用" prop="otherExpenses">
                    <el-input v-model="ruleForm.otherExpenses" :disabled="selectedApplyId!=null"/>
                </el-form-item>

                <h3 style="text-align: center;" v-if="selectedApplyId!=null">审核结果</h3>
                <el-form-item label="审核结果" prop="isPass" v-if="selectedApplyId!=null">
                    <el-input v-model="ruleForm.isPass" :disabled="selectedApplyId!=null"/>
                </el-form-item>
                <el-form-item label="审核人" prop="auditorName" v-if="selectedApplyId!=null">
                    <el-input v-model="ruleForm.auditorName" :disabled="selectedApplyId!=null"/>
                </el-form-item>
                <el-form-item label="审核时间" prop="auditTime" v-if="selectedApplyId!=null">
                    <el-input v-model="ruleForm.auditTime" :disabled="selectedApplyId!=null"/>
                </el-form-item>
                <el-form-item label="审核意见" prop="opinions" v-if="selectedApplyId!=null">
                    <el-input v-model="ruleForm.opinions" type="textarea":disabled="selectedApplyId!=null"/>
                </el-form-item>

                <el-form-item v-if="selectedApplyId==null">
                    <el-button type="primary" @click="submitForm(ruleFormRef)" :disabled="submitButtonIsDisabled">
                        提交申请
                    </el-button>
                    <el-button @click="resetForm(ruleFormRef)" :disabled="resetButtonIsDisabled">重置</el-button>
                    <el-button @click="exit">退出</el-button>
                </el-form-item>
            </el-form>
        </div>
        <div class="right-box">
            <h2 style="text-align: center;">选择要申请的任务</h2>
            <div class="task-list">
                <el-empty v-if="taskData.length == 0" description="你没有需要申请的任务" />
                <el-radio-group v-model="selectedTaskId"
                    style="display:flex;flex-direction:column;align-items:baseline;padding-left: 5px">
                    <el-radio @change="setSelectedTask(task)" v-for="task in taskData" :key="task.taskId"
                        :value="task.taskId" size="large" style="margin:5px 0 0 0;white-space:unset;min-height:50px;height:auto;width: 95%;" border>
                        任务名称：{{task.taskName }}
                    </el-radio>
                </el-radio-group>
            </div>
            <h2 style="text-align: center;">我的申请记录</h2>
            <div class="application-list">
                <el-empty v-if="myApplication.length == 0" description="你没有申请记录" />
                <el-radio-group v-model="selectedApplyId"
                    style="display:flex;flex-direction:column;align-items:baseline;padding-left: 5px">
                    <el-radio @change="setSelectedApplication(application)" v-for="application in myApplication"
                        :key="application.applyId" :value="application.applyId" size="large"
                        style="margin:5px 0 0 0;white-space:unset;min-height:50px;height:auto;width: 95%;" border>
                        <p>申请时间:{{application.applyTime }}</p>
                        <p style="margin-top:3px;border-top: 1px solid rgba(148, 143, 143,0.3);">任务名称：{{application.taskName }}</p>
                    </el-radio>
                </el-radio-group>
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

.apply-form {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 75%;
    min-width: 50%;
    height: 100%;
    border-right: 2px solid rgb(148, 143, 143);
    overflow: auto;
    -ms-overflow-style: none;
    /* IE and Edge */
    scrollbar-width: none;
    /* Firefox */
}

/* 隐藏 Chrome、Safari 和 Opera 的滚动条 */
.apply-form::-webkit-scrollbar {
    display: none;
}

.task-list::-webkit-scrollbar {
    display: none;
}
.task-list {
    width: 100%;
    overflow: auto;
    border-bottom: 1px solid bisque; 
    height: 45%;
    max-height: 50%;
    -ms-overflow-style: none;
    /* IE and Edge */
    scrollbar-width: none;
    /* Firefox */
}

.application-list::-webkit-scrollbar {
    display: none;
}
.application-list {
    width: 100%;
    overflow: auto;
    height: 45%;
    max-height: 50%;
    -ms-overflow-style: none;
    /* IE and Edge */
    scrollbar-width: none;
    /* Firefox */
}


.right-box {
    width: 25%;
    height: 100%;
    overflow: auto;
    -ms-overflow-style: none;
    /* IE and Edge */
    scrollbar-width: none;
    /* Firefox */
}
</style>