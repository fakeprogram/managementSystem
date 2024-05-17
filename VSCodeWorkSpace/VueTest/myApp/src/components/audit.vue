<script setup>
import { Menu as IconMenu, Message, Setting, List } from '@element-plus/icons-vue'
import { createRouter, createWebHistory, useRoute, useRouter } from "vue-router";
import axios from "axios";
import { ref, reactive, toRefs, onMounted, onBeforeMount } from "vue";
import { ElMessage, ElMessageBox } from 'element-plus';
import router from '@/router/router';

// var ruleForm = reactive({
//     taskName: '',
//     address: '',
//     userName: JSON.parse(localStorage.getItem("user")).user_name,
//     departmentName: JSON.parse(localStorage.getItem("user")).departmentName,
//     departureTime: '',
//     returnTime: '',
//     vehicle: '',
//     totalCost: null,
//     transportationExpense: null,
//     hotelExpense: null,
//     cateringExpenses: null,
//     otherExpenses: null,
//     days: null,
// })

//申请数据
var applicationData = ref([])
var auditForm = ref({
    taskName: '',
    auditor: JSON.parse(localStorage.getItem("user")).user_name,
    isPass: '',
    opinions: '',
})

const tableRef = ref(null)
const auditFormRf = ref(null)
const rules = reactive({
    taskName: [
        { required: true, message: '请选择要审核的申请', trigger: 'blur' }
    ],
    auditor: [
        { required: true, message: '审核人错误', trigger: 'blur' }
    ],
    isPass: [
        { required: true, message: '请选择是否通过', trigger: 'blur' }
    ],
    opinions: [
        { required: true, message: '请输入审核意见', trigger: 'blur' }
    ],
})

/**
 * 选中数据
 */
const multipleSelection = ref([])
const handleSelectionChange = (val) => {
    multipleSelection.value = val
    if (val.length > 0) {
        auditForm.value.taskName = val[0].taskName
    }
    else {
        auditForm.value.taskName = ''
    }
    console.log(multipleSelection.value)
}
onMounted(() => {
    ElMessage({
        message: "正在获取申请数据",
        type: 'success',
    })
    getData()
})

/*
* 获取数据
*/
function getData() {
    axios({
        url: "/api/audit/getUnAuditedApplication",
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
                applicationData.value = response.data.data
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
 * 提交审核
 */
function submitAudit() {
    if (multipleSelection.value.length == 0) {
        ElMessage({
            message: "请选择要审核的申请",
            type: 'warning',
        })
        return;
    }
    else {
        ElMessageBox.confirm('确定要提交审核吗？', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
        })
            .then(() => {
                var audit = {
                    "applyId": multipleSelection.value[0].applyId,
                    "auditor": JSON.parse(localStorage.getItem("user")).user_id,
                    "opinions": auditForm.value.opinions,
                    "isPass": auditForm.value.isPass === "通过" ? 1 : 0
                }
                axios({
                    url: "/api/audit/auditOperation",
                    method: "post",
                    data: audit,
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
 *  取消审核
 */
function cancelAudit() {
    multipleSelection.value = []
    console.log(tableRef.value)
    tableRef.value.clearSelection()
    auditForm.value.taskName = ''
    resetForm(auditFormRf.value)
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
                    <List />待审核的申请
                </el-icon>
            </div>
            <el-scrollbar>
                <el-table empty-text="暂无数据" :data="applicationData" ref="tableRef"
                    @selection-change="handleSelectionChange" style="width: 100%">
                    <el-table-column type="expand">
                        <template #default="props">
                            <div style="padding:10px 60px ;">
                                <el-form style="max-width: 750px;min-width: 700px;" label-width="auto"
                                    class="demo-ruleForm" :size="formSize" status-icon>
                                    <el-form-item label="任务名称" prop="taskName">
                                        <el-input v-model="props.row.taskName" :placeholder="props.row.taskName"
                                            disabled />
                                    </el-form-item>
                                    <el-form-item label="申请人" prop="userName">
                                        <el-input :placeholder="props.row.userName" disabled />
                                    </el-form-item>
                                    <el-form-item label="部门" prop="departmentName">
                                        <el-input :placeholder="props.row.departmentName" disabled />
                                    </el-form-item>
                                    <el-form-item label="出差地址" prop="address">
                                        <el-input :placeholder="props.row.address" disabled />
                                    </el-form-item>
                                    <el-form-item label="交通工具" prop="vehicle">
                                        <el-input :placeholder="props.row.vehicle" disabled />
                                    </el-form-item>
                                    <el-form-item label="出发时间" prop="departureTime">
                                        <el-date-picker type="datetime" :placeholder="props.row.departureTime"
                                            value-format="YYYY-MM-DD HH:mm:ss" disabled />
                                    </el-form-item>

                                    <el-form-item label="返回时间" prop="returnTime">
                                        <el-date-picker type="datetime" :placeholder="props.row.returnTime"
                                            value-format="YYYY-MM-DD HH:mm:ss" disabled />
                                    </el-form-item>

                                    <el-form-item label="出差天数" prop="days">
                                        <el-input :placeholder="props.row.days" disabled />
                                    </el-form-item>

                                    <el-form-item label="申请总费用" prop="totalCost">
                                        <el-input :placeholder="props.row.totalCost" disabled />
                                    </el-form-item>

                                    <el-form-item label="预计交通费" prop="transportationExpense">
                                        <el-input :placeholder="props.row.transportationExpense" disabled />
                                    </el-form-item>
                                    <el-form-item label="预计住宿费" prop="hotelExpense">
                                        <el-input :placeholder="props.row.hotelExpense" disabled />
                                    </el-form-item>
                                    <el-form-item label="预计餐饮费" prop="cateringExpenses">
                                        <el-input :placeholder="props.row.cateringExpenses" disabled />
                                    </el-form-item>
                                    <el-form-item label="预计其他费用" prop="otherExpenses">
                                        <el-input :placeholder="props.row.otherExpenses" disabled />
                                    </el-form-item>
                                </el-form>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column type="selection" width="55" :selectable="isSelectable" />
                    <el-table-column prop="userName" label="申请人" width="120" />
                    <el-table-column prop="applyTime" label="申请时间" width="300" />
                    <el-table-column prop="departmentName" label="申请人部门" width="120" />
                    <el-table-column prop="taskName" label="任务名称" width="300" />
                </el-table>
            </el-scrollbar>
        </el-main>
        <el-aside>
            <div style="width: 100%; height: 5%; margin-top:30%;background-color: rgba(163, 171, 171,0.4);">
                <el-icon class="avatar-uploader-icon">
                    <List />填写审核信息
                </el-icon>
            </div>
            <div class="audit-form-container">
                <el-form ref="auditFormRf" label-position="right" style="margin: 20px 0 0 0;" :model="auditForm"
                    :rules="rules">
                    <el-form-item label="任务名称">
                        <el-input v-model="auditForm.taskName" type="textarea" disabled />
                    </el-form-item>
                    <el-form-item label="审核人" prop="auditor">
                        <el-input v-model="auditForm.auditor" disabled />
                    </el-form-item>
                    <el-form-item label="审核结果" prop="isPass">
                        <el-select v-model="auditForm.isPass" placeholder="请选择是否通过">
                            <el-option label="不通过" value="不通过" />
                            <el-option label="通过" value="通过" />
                        </el-select>
                    </el-form-item>
                    <el-form-item label="审核意见" prop="opinions">
                        <el-input v-model="auditForm.opinions" type="textarea" />
                    </el-form-item>
                </el-form>
                <el-form-item>
                    <el-button type="primary" @click="submitForm(auditFormRf)" :disabled="submitButtonIsDisabled">
                        提交
                    </el-button>
                    <el-button @click="resetForm(auditFormRf)" :disabled="resetButtonIsDisabled">重置</el-button>
                    <el-button @click="cancelAudit">取消审核该任务</el-button>
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