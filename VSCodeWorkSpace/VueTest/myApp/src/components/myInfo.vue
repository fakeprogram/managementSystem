<script setup>
import { ref } from 'vue'
const userInfo = ref(JSON.parse(localStorage.getItem('user'))) 
console.log(userInfo.value)
</script>


<template>
    <el-card>
        <el-descriptions class="margin-top" title="个人信息" :column="2" border>
            <el-descriptions-item>
                <template #label>
                    头像
                </template>
                <img class="img"
                    :src="userInfo.head"
                    alt="" />
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    姓名
                </template>
                {{ userInfo.user_name }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    部门
                </template>
                {{userInfo.departmentName}}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    岗位
                </template>
                AAAA
            </el-descriptions-item>

            <el-descriptions-item>
                <template #label>
                    年龄
                </template>
                30
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    性别
                </template>
                男
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    邮箱Email
                </template>
                {{ userInfo.user_email }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    手机号码
                </template>
                {{userInfo.user_tel}}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    住址
                </template>
                成都市
            </el-descriptions-item>

            <!-- <el-descriptions-item>
                <template #label>
                    <i class="el-icon-basketball"></i>
                    兴趣爱好
                </template>
                写代码
            </el-descriptions-item> -->
            <!-- <el-descriptions-item>
                <template #label>
                    <i class="el-icon-magic-stick"></i>
                    个性签名
                </template>
                军师
            </el-descriptions-item> -->
            <el-descriptions-item>
                <template #label>
                    <i class="el-icon-date"></i>
                    注册日期
                </template>
                2024-4-24
            </el-descriptions-item>
        </el-descriptions>
    </el-card>
</template>

<style scoped>
.img {
  width: 80px;
  height: 80px;
}
</style>