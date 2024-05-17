import { createRouter, createWebHistory} from "vue-router";
import taskManagement from "@/components/taskManagement.vue";
import baiDuMap from "@/components/baiDuMap.vue";
import me from "@/components/me.vue";
import outsideStaff from "@/components/outsideStaff.vue";
import taskOverview from "@/components/taskOverview.vue";
import system from "@/view/system.vue";
import login from "@/view/login.vue";
import OnGoningTaskList from "@/components/onGoningTaskList.vue";
import pastTask from "@/components/pastTask.vue";
import ReleaseTask from "@/components/releaseTask.vue";
import Audit from "@/components/audit.vue";
import ReleasedByMyself from "@/components/releasedByMyself.vue";
import Application from "@/components/application.vue";
import myInfo from "@/components/myInfo.vue";
import myCurrentTask from "@/components/myCurrentTask.vue";
import myHistoryTask from "@/components/myHistoryTask.vue";
import myNewTask from "@/components/myNewTask.vue";
import Attendance from "@/components/attendance.vue";

function loginCheck(){
    if(localStorage.getItem('token')){
        return '/system/homeIndex'
    }else{
        return '/login'
    }
}

const routes = [
    {
        path:'/',
        redirect:loginCheck()
    },
    {
        path: '/login',
        name:'login',
        component: login
    },
    {
        path: '/system',
        name:'system',
        component: system,
        children:[
            {
                path: 'homeIndex',
                name:'homeIndex',
                component: baiDuMap
            },
            {
                path: 'taskOverview',
                name:'taskOverview',
                component: taskOverview,
            },
            {
                path: 'taskManagement',
                name:'taskManagement',
                component: taskManagement,
                redirect:'/system/taskManagement/onGoingTask',
                children:[
                    {
                        path: 'onGoingTask',
                        name:'onGoingTask',
                        component: OnGoningTaskList,
                    },
                    {
                        path: 'pastTask',
                        name:'pastTask',
                        component: pastTask,
                    },
                    {
                        path: 'releasedByMyself',
                        name:'releasedByMyself',
                        component:ReleasedByMyself,
                    },
                    {
                        path: 'releaseTask',
                        name:'releaseTask',
                        component: ReleaseTask,
                    },
                    {
                        path: 'audit',
                        name:'audit',
                        component: Audit,
                    }
                ]
            },
            {
                path: 'outsideStaff',
                name:'outsideStaff',
                component: outsideStaff
            },
            {
                path: 'me',
                name:'me',
                component: me,
                redirect:'/system/me/myInfo',
                children:[
                    {
                        path: 'myInfo',
                        name:'myInfo',
                        component:myInfo,
                    },
                    {
                        path: 'attendance',
                        name:'attendance',
                        component:Attendance,
                    },
                    {
                        path: 'myCurrentTask',
                        name:'myCurrentTask',
                        component: myCurrentTask,
                    },
                    {
                        path: 'myHistoryTask',
                        name:'myHistoryTask',
                        component:myHistoryTask,
                    },
                    {
                        path: 'myNewTask',
                        name:'myNewTask',
                        component:myNewTask,
                    },
                    {
                        path: 'apply',
                        name:'apply',
                        component:Application,
                    },
                ]
            }
        ]
    },
]

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes:routes
})

export default router