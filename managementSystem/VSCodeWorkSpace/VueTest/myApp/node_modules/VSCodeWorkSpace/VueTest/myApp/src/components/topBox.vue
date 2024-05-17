<script setup>
import { roleTypes } from "element-plus";
import { ref, onMounted, onBeforeMount } from "vue";

const roleType = ref(false)
onMounted(() => {
    roleType.value = roleCheck()
})

function roleCheck() {
    var role = JSON.parse(localStorage.getItem('user')).role_type
    if (role === 1) {
        return false
    } else {
        return true
    }
}
</script>

<!--  -->
<template>
    <div class="topcontainer" id="topBox">
        <ul>
            <router-link to="/system/homeIndex">
                首页
            </router-link>
            <router-link to="/system/taskOverview" v-if="roleType">
                任务总览
            </router-link>
            <router-link to="/system/taskManagement" v-if="roleType">
                任务管理
            </router-link>
            <router-link to="/system/outsideStaff">
                在外人员
            </router-link>
            <router-link to="/system/me">
                个人中心
            </router-link>
        </ul>
    </div>
</template>

<!--  -->
<style scoped>
#topBox {
    position: relative;
    top: 0;
    width: 100%;
    height: 10%;
    /* background-color: aquamarine; */
    /* border: 1px solid white; */
    box-sizing: border-box;
    z-index: 6;
    align-items: center;
}

ul {
    /* position:absolute; */
    display: block;
    height: 100%;
    list-style: none;
    align-items: center;
    background-color: rgb(50, 49, 51);
    /* z-index: 5; */
}

a {
    position: relative;
    display: inline-block;
    width: 16.6%;
    height: 100%;
    text-align: center;
    line-height: 73px;
    box-sizing: border-box;
}

a:hover {
    background-color: rgb(110, 112, 113);
    background-image: url("/button4.png");
    background-position: center;
    background-size: 100% 100%;
    background-repeat: no-repeat;
}


.router-link-active {
    text-decoration: none;
    /* color: yellow; */
    background-image: url("/button4.png");
    background-position: center;
    background-size: 100% 100%;
    background-repeat: no-repeat;
}

a {
    text-decoration: none;
    color: white;
}
</style>