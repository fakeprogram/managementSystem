<script setup>
import { ref } from 'vue'
import { Menu as IconMenu, Message, Setting } from '@element-plus/icons-vue'
import { useRouter, useRoute } from 'vue-router';
import { ElMessageBox } from 'element-plus';

const route = useRoute()
const router = useRouter()

function logout() {
  ElMessageBox.confirm('确定要注销吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  })
    .then(() => {
      localStorage.removeItem('user')
      localStorage.removeItem('token')
      router.push({
        name: 'login'
      })
    })
}
</script>
<template>
  <el-container class="layout-container-demo" style="height:90%">
    <el-aside width="250px">
      <el-scrollbar>
        <el-menu>
          <el-menu-item index="1" @click="() => { $router.push({ name: 'myInfo' }) }">个人基本信息</el-menu-item>
          <el-menu-item index="2" @click="() => { $router.push({ name: 'attendance' }) }">考勤</el-menu-item>
          <el-sub-menu index="3">
            <template #title>
              我的任务
            </template>
            <el-menu-item index="3-1" @click="() => { $router.push({ name: 'myCurrentTask' }) }">当前任务</el-menu-item>
            <el-menu-item index="3-2" @click="() => { $router.push({ name: 'myHistoryTask' }) }">历史任务</el-menu-item>
            <el-menu-item index="3-3" @click="() => { $router.push({ name: 'myNewTask' }) }">待签收任务</el-menu-item>
            <el-menu-item index="3-4" @click="() => { $router.push({ name: 'apply' }) }">出差申请</el-menu-item>
          </el-sub-menu>
          <!-- <el-menu-item index="5">我的出差数据统计</el-menu-item> -->
          <el-menu-item index="4" @click="logout">注销登陆</el-menu-item>
        </el-menu>
      </el-scrollbar>
    </el-aside>

    <el-container>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<style scoped>
.layout-container-demo .el-header {
  position: relative;
  background-color: var(--el-color-primary-light-7);
  color: var(--el-text-color-primary);
}

.layout-container-demo .el-aside {
  color: var(--el-text-color-primary);
  border-right: 1px solid rgb(108, 105, 105);
  /* background: var(--el-color-primary-light-8); */
}

.layout-container-demo .el-menu {
  border-right: none;
}

.layout-container-demo .el-main {
  padding: 0;
}

.layout-container-demo .toolbar {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  right: 20px;
}

.el-descriptions {
  margin-top: 20px;
}

.cell-item {
  display: flex;
  align-items: center;
}

.margin-top {
  margin-top: 20px;
}
</style>
