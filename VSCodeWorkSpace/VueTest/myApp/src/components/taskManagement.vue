<script setup>
import { ref } from 'vue'
import { Menu as IconMenu, Message, Setting } from '@element-plus/icons-vue'
import taskList from './onGoningTaskList.vue';



</script>

<template>
    <el-container class="layout-container-demo" style="height: 90%">
        <el-aside width="300px">
            <el-scrollbar>
                <el-menu>
                    <el-menu-item index="1">
                        <template #title>
                            <router-link :to="{ name:'onGoingTask'}">
                                正在进行
                            </router-link>
                        </template>
                    </el-menu-item>

                    <el-menu-item index="2">
                        <template #title>
                            <router-link :to="{ name:'pastTask'}">
                                历史任务
                            </router-link>
                        </template>
                    </el-menu-item>

                    <el-menu-item index="3">
                        <template #title>
                            <router-link :to="{ name:'releasedByMyself'}">
                                任务发布
                            </router-link>
                        </template>
                    </el-menu-item>

                    <el-menu-item index="4">
                        <template #title>
                            <router-link :to="{ name:'audit'}">
                                出差审批
                            </router-link>
                        </template>
                    </el-menu-item>
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
    border-right: 1px solid rgb(36, 36, 39);
    background: var(--el-color-primary-light-8);
    padding: 0
}

.layout-container-demo .el-menu {
    border-right: none;
    padding: 0;
}

.el-menu-item {
    background: var(--el-color-primary-light-8);
    width: 100%;
    height: 100%;
    padding: 0;
    border-bottom: 1px solid rgba(36, 36, 39,0.3);
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

.router-link-active {
    text-decoration:none;
    color:yellow;
    background-color: rgb(81, 77, 77)
}

a{
    text-decoration: none;
    display: block;
    width: 100%;
    height: 100%;
    color: black;
    text-align: center;
    padding: 0
}
</style>