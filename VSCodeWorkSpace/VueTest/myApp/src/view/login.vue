<script setup>
import axios from 'axios';
import { ref, reactive } from 'vue';
import { useRouter } from 'vue-router'
// Element-Plus的依赖采用的是自动导入，所以这里无需显示的引入，其他地方的element-plus引用同理
import { ElMessage } from 'element-plus';
import { Calendar, Search,User,Lock } from '@element-plus/icons-vue'
//import request from '@/utils/request';      //这里使用自行封装的axios，下文已给出，照搬后修改运行端口即可
const router = useRouter()

// const checkCodeUrl = "api/checkCode?" + new Date().getTime();
//表单
const formDataRef = ref();
let formData = reactive({
    user_tel: "",
    pass_word: ""
});
const rules = {
    user_tel: [{
        required: true,
        message: "请输入用户名"
    }],
    pass_word: [{
        required: true,
        message: "请输入密码"
    }],
    // checkCode: [{
    //     required: true,
    //     message: "请输入验证码"
    // }],
}


const login = () => {
    var form_obj = JSON.parse(JSON.stringify(formData));
    // console.log(form_obj);
    // console.log(form_obj.user_tel);
    // console.log(form_obj.pass_word);
    axios.post("api/login", form_obj).then(res => {
        if (res.data.success == true) {
            ElMessage({
                message: '登录成功',
                type: 'success',
            })
            console.log("res", res);
            let token = res.data.token;
            let user = res.data.user;
            localStorage.removeItem("token")
            localStorage.removeItem("user")
            localStorage.setItem("token", token);
            localStorage.setItem("user", JSON.stringify(user));
            console.log(localStorage.getItem("token"))
            console.log(JSON.parse(localStorage.getItem("user")))
            // let tokenObj = { token: " isLogin", startTime: new Date().getTime() };
            // window.localStorage.setItem("isLogin", JSON.stringify(tokenObj));
            // localStorage.setItem("user_tel", JSON.parse(JSON.stringify(formData.user_tel)));
            router.push("/system/homeIndex");
        } else {
            ElMessage.error('账号或密码错误！！！登录失败！！！')
        }
    });
};

</script>

<template>
    <div class="login-body">
        <div class="login-panel">
            <div class="login-title">用户登录</div>
            <el-form :model="formData" :rules="rules" ref="formDataRef">
                <el-form-item prop="user_tel">
                    <el-input placeholder="请输入账号" v-model="formData.user_tel" size="large" type="text">
                        <template #prefix>
                            <el-icon >
                                <User />
                            </el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="pass_word">
                    <el-input placeholder="请输入密码" v-model="formData.pass_word" size="large" type="password" show-password @keyup.enter.native="login()">
                        <template #prefix>
                            <el-icon>
                                <Lock/>
                            </el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <!-- <el-form-item label="">
                    <div class="check-code-panel">
                        <el-input placeholder="请输入验证码" v-model="formData.checkCode" class="input-panel" />
                        <img src="checkCodeUrl" class="check-code">
                    </div>
                </el-form-item> -->
                <!-- <el-form-item label="">
                    <el-checkbox label="记住密码" name="type" />
                </el-form-item> -->
                <el-form-item label="">
                    <el-button type="primary" style="width: 100%;" @click="login()" size="large">登录</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<style scoped>
.login-body {
    background: url("") no-repeat center center;
    height: 100%;
    width: 100%;
    background-size: cover;
    position: absolute;
    left: 0;
    top: 0;
}

.login-panel {
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    margin: auto;
    padding: 25px;
    width: 26%;
    min-width: 460px;
    height: 30%;
    min-height: 300px;
    background: rgba(255, 255, 255, 0.6);
    border-radius: 5%;
    box-shadow: 2px 2px 10px #ddd;
}

.login-title {
    font-size: 22px;
    text-align: center;
    margin-bottom: 22px;
}
</style>