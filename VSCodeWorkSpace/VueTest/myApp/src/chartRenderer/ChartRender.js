import * as echarts from 'echarts'
import axios from 'axios';
import { ElMessage, formatter } from 'element-plus';

//图表一
function chart1dataprocess(chartname, chart, option) {
    chart.setOption(option);
}
export function chart1(dom, chartname, chartdata) {
    // chart1dataprocess(chartdata);
    let Chart = new echarts.init(dom);
    // 绘制图表
    let option = {
        title: {
            text: chartname,
            left: 'center',
            textStyle: {
                color: 'white'
            }
        },
        tooltip: {
            trigger: 'item',
            confine: true,
            renderMode: 'richText',//否则显示会有bug
        },
        legend: {
            orient: 'vertical',
            left: 'right',
            // right:'right',
            top: 'center',
            textStyle: {
                color: 'white'
            }
        },
        grid: { // 让图表占满容器
            top: "0px",
            left: "0px",
            right: "0px",
            bottom: "0px",
            containLabel: true,
        },

        // xAxis: {
        //     type: 'category',
        //     name: '名称',
        //     textStyle: {
        //         color: 'white',
        //     }
        // },
        // yAxis: {
        //     type: 'value',
        //     name: "出差天数"
        // },

        series: [
            {
                // name: 'Access From',
                type: 'pie',
                radius: '50%',
                data: chartdata,
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgb(0, 0, 0)'
                    }
                }
            }
        ]
    };
    // 渲染图表
    Chart.setOption(option);
    // chart1dataprocess('aaa',Chart,option)
}


/**
 * 首页考勤图表
 * @param {*} dom 
 * @param {*} chartname 
 * @param {*} chartdata 
 */
export function homeAttendance(dom, chartname, chartdata) {
    let Chart = echarts.init(dom);
    // 绘制图表
    let option = {
        title: {
            text: chartname,
            left: 'center',
            textStyle: {
                color: 'white'
            }
        },
        tooltip: {
            trigger: 'item',
            confine: true,
            // formatter: '{b}:{c}人',
            renderMode: 'richText',//否则显示会有bug
        },
        legend: {
            orient: 'vertical',
            right:'20px',
            // right:'right',
            top: 'center',
            textStyle: {
                color: 'white'
            }
        },
        // grid: { // 让图表占满容器
        //     top: "0px",
        //     left: "0px",
        //     right: "0px",
        //     bottom: "0px",
        //     containLabel: true,
        // },

        series: [
            {
                type: 'pie',
                radius: '70%',
                center: ['40%', '50%'],

                data: chartdata,
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                },
                label: {
                    // show: true,
                    position: 'inside',
                    color: 'rgba(255, 255, 255)',
                    formatter: function (e) {
    
                        let data = e.data;
                        if (data.value == 0) {
                            return ''
                        } else {
                            return data.name + ':' + data.value + '人';
                        }
                    },
                    textStyle: {
                        color: 'white',
                        //   fontSize: 20
                    }
                },
            }
        ]
    };
    // 渲染图表
    Chart.setOption(option);
}

/**
 * 首页本年出差天数排名
 * @param {*} dom 
 * @param {*} chartname 
 */
export function homePersonalBusinessTravelTimeRankings(dom, chartname) {
    let Chart = echarts.init(dom);
    let data = []
    let option = {
        dataset: {
            dimensions: ['user_name', 'allDays'],
            source: data
        },
        title: {
            text: chartname,
            left: 'center',
            textStyle: {
                color: 'white'
            }
        },
        tooltip: {
            trigger: 'axis',
            confine: true,
            formatter: function (params) {
                return params[0].value.user_name + ':' + params[0].value.allDays + '天'
            },
            renderMode: 'richText',//否则显示会有bug
        },
        // legend: {
        //     orient: 'vertical',
        //     left: 'right',
        //     // right:'right',
        //     top: 'center',
        //     textStyle: {
        //         color: 'white'
        //     }
        // },
        grid: { // 让图表占满容器
            // top: "0px",
            left: "2px",
            right: "45px",
            bottom: "0px",
            containLabel: true,
        },
        xAxis: {
            type: 'category',
            name: '名称',
            nameTextStyle: {
                color: 'white'
            },
            axisLine: {
                lineStyle: {
                    color: 'white'
                },
            },
        },
        yAxis: {
            type: 'value',
            name: "出差天数",
            nameTextStyle: {
                color: 'white'
            },
            axisLine: {
                lineStyle: {
                    color: 'white'
                },
            },
        },
        series: [
            {
                type: 'bar',
                // data: data,
                encode: {
                    x: 'user_name',
                    y: 'allDays'
                },
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                },
            }
        ]
    }

    Chart.setOption(option)
    // 获取排名数据
    axios({
        url: "/api/departmentStatistic/getPersonalBusinessTravelTimeRankings",
        method: "get",
        headers: {
            'Content-Type': 'application/json',
            "token": localStorage.getItem("token"),
        }
    })
        .then(function (response) {
            if (response.data.success === true) {
                for (let i = 0; i < response.data.data.length && i < 10; i++) {
                    data.push(response.data.data[i])
                }

                Chart.setOption(option);
            } else {
                ElMessage.error("未能获取到排名数据")
            }
        })
        .catch(function (error) {
            // ElMessage.error("操作失败")
            console.log(error);
        });
}

/**
 * 在外人员位置分布
 * @param {*} dom 
 * @param {*} chartname 
 */
export function geographicalDistributionOfExpatriates(dom, chartname, chartdata) {
    let Chart = echarts.init(dom);
    let data = [];
    data.push(chartdata);

    // 绘制图表
    let option = {
        title: {
            text: chartname,
            left: 'center',
            textStyle: {
                color: 'white'
            }
        },
        tooltip: {
            trigger: 'item',
            confine: true,
            // formatter: '{b}',
            renderMode: 'richText',//否则显示会有bug
        },
        legend: {
            orient: 'vertical',
            left: 'right',
            // right:'right',
            top: 'center',
            textStyle: {
                color: 'white'
            }
        },
        grid: { // 让图表占满容器
            top: "0px",
            left: "0px",
            right: "0px",
            bottom: "0px",
            containLabel: true,
        },

        series: [
            {
                type: 'pie',
                radius: '70%',
                data: data,
                center: ['30%', '50%'],
                itemStyle: {
                    shadowBlur: 0,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                },
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 10,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                },
                label: {
                    // show: true,
                    position: 'inside',
                    color: 'rgba(255, 255, 255)',
                    formatter: '{b}:{c}人',
                    // formatter: function (e) {
                    //     console.log(e)
                    //     let data = e.data;
                    //     if (data.value == 0) {
                    //         return ''
                    //     } else {
                    //         return data.name + ':' + data.value + '人';
                    //     }
                    // },
                    textStyle: {
                        color: 'white',
                        //   fontSize: 20
                    }
                },
            }
        ]
    };
    // 渲染图表
    Chart.setOption(option);
    // 获取名数据
    axios({
        url: "/api/departmentStatistic/geographicalDistributionOfExpatriates",
        method: "get",
        headers: {
            'Content-Type': 'application/json',
            "token": localStorage.getItem("token"),
        }
    })
        .then(function (response) {
            if (response.data.success === true) {
                for (let i = 0; i < response.data.data.length; i++) {
                    data.push(
                        { name: response.data.data[i].city, value: response.data.data[i].num }
                    )
                }

                Chart.setOption(option);//重新渲染图表
            } else {
                ElMessage.error("未能获取到位置数据")
            }
        })
        .catch(function (error) {
            // ElMessage.error("操作失败")
            console.log(error);
        });
}

/**
 * XX部门本月支出类型占比
 * @param {*} dom 
 * @param {*} chartname 
 * @param {*} departmentId 
 */
export function proportionOfDepartmentalExpenditureTypes(dom, chartname, departmentId) {
    let Chart = echarts.init(dom);
    let data = [];
    // 绘制图表
    let option = {
        title: {
            text: chartname,
            left: 'center',
            textStyle: {
                color: 'white'
            }
        },
        tooltip: {
            trigger: 'item',
            confine: true,
            // formatter: '{b}',
            renderMode: 'richText',//否则显示会有bug
        },
        legend: {
            orient: 'vertical',
            left: 'right',
            // right:'right',
            top: 'center',
            textStyle: {
                color: 'white'
            }
        },
        grid: { // 让图表占满容器
            top: "0px",
            left: "0px",
            right: "0px",
            bottom: "0px",
            containLabel: true,
        },

        series: [
            {
                type: 'pie',
                radius: '70%',
                data: data,
                // data:[{
                //     value:200,name:'ddd',
                // }],
                center: ['40%', '50%'],
                itemStyle: {
                    shadowBlur: 0,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                },
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 10,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                },
                label: {
                    // show: true,
                    position: 'inside',
                    color: 'rgba(255, 255, 255)',
                    formatter: '{d}%',
                    // formatter: function (e) {
                    //     console.log(e)
                    //     let data = e.data;
                    //     if (data.value == 0) {
                    //         return ''
                    //     } else {
                    //         return data.name + ':' + data.value + '人';
                    //     }
                    // },
                    textStyle: {
                        color: 'white',
                        //   fontSize: 20
                    }
                },
            }
        ]
    };
    // 渲染图表
    Chart.setOption(option);
    // 获取名数据
    axios({
        url: "/api/departmentStatistic/proportionOfDepartmentalExpenditureTypes",
        method: "get",
        params: { "departmentId": departmentId },
        headers: {
            'Content-Type': 'application/json',
            "token": localStorage.getItem("token"),
        }
    })
        .then(function (response) {
            if (response.data.success === true && response.data.data != null) {

                data.push({ value: response.data.data.transportation_expense, name: "交通费" })
                data.push({ value: response.data.data.hotel_expense, name: "住宿费" })
                data.push({ value: response.data.data.catering_expenses, name: "餐饮费" })
                data.push({ value: response.data.data.other_expenses, name: "其他费用" })

                Chart.setOption(option, 'dark');//重新渲染图表
            } else {
                // ElMessage.error("未能获取到支出数据")
            }
        })
        .catch(function (error) {
            // ElMessage.error("操作失败")
            console.log(error);
        });
}

/**
 * XX部门年度支出
 * @param {*} dom 
 * @param {*} chartname 
 * @param {*} departmentId 
 */
export function departmentalAnnualExpenditure(dom, chartname, departmentId) {
    let Chart = echarts.init(dom);
    let data = [
        { value: 0, name: '一月' },
        { value: 0, name: '二月' },
        { value: 0, name: '三月' },
        { value: 0, name: '四月' },
        { value: 0, name: '五月' },
        { value: 0, name: '六月' },
        { value: 0, name: '七月' },
        { value: 0, name: '八月' },
        { value: 0, name: '九月' },
        { value: 0, name: '十月' },
        { value: 0, name: '十一月' },
        { value: 0, name: '十二月' },
    ];
    let option = {
        dataset: {
            dimensions: ['name', 'value'],
            source: data
        },
        title: {
            text: chartname,
            left: 'center',
            textStyle: {
                color: 'white'
            }
        },
        tooltip: {
            trigger: 'none',
            axisPointer: {
                type: 'cross'
            }
        },
        legend: {},
        grid: {
            top: 30,
            left: 40,
            right: 40,
            bottom: 30
        },
        xAxis:
        {
            type: 'category',
            name: "月份",
            nameTextStyle: {
                color: 'white'
            },
            axisLine: {
                lineStyle: {
                    color: 'white'
                },
            },
            axisTick: {
                alignWithLabel: true
            },
            // axisLine: {
            //     onZero: false,
            //     lineStyle: {
            //         // color:"green"
            //     }
            // },
            axisPointer: {
                label: {
                    formatter: function (params) {
                        return params.seriesData[0].data.name + ":" + params.seriesData[0].data.value
                    },
                    textStyle: {
                        color: 'black'
                    }
                }
            },
            // prettier-ignore
        },
        yAxis:
        {
            type: 'value',
            name: "支出/元",
            nameTextStyle: {
                color: 'white'
            },
            axisLine: {
                lineStyle: {
                    color: 'white'
                },
            },
            axisPointer: {
                label: {
                    textStyle: {
                        color: 'black'
                    }
                }
            },
        },
        series: [
            {
                type: 'line',
                smooth: true,
                emphasis: {
                    focus: 'series'
                },
                lineStyle: {
                    color: "#51A7FE"
                }
                // data: data,
            }
        ]
    };
    Chart.setOption(option)
    // 获取名数据
    axios({
        url: "/api/departmentStatistic/departmentalAnnualExpenditure",
        method: "get",
        params: { "departmentId": departmentId },
        headers: {
            'Content-Type': 'application/json',
            "token": localStorage.getItem("token"),
        }
    })
        .then(function (response) {
            if (response.data.success === true && response.data.data != null) {

                var tempData = response.data.data
                for (var i = 0; tempData != null && i < tempData.length; i++) {
                    console.log("temp", tempData)
                    // January, February, March, April, May, June, July, August, September, October, November, December
                    switch (tempData[i].month) {
                        case "January":
                            data[0].value = tempData[i].total_cost
                            break;
                        case "February":
                            data[1].value = tempData[i].total_cost
                            break;
                        case "March":
                            data[2].value = tempData[i].total_cost
                            break;
                        case "April":
                            data[3].value = tempData[i].total_cost
                            break;
                        case "May":
                            data[4].value = tempData[i].total_cost
                            break;
                        case "June":
                            data[5].value = tempData[i].total_cost
                            break;
                        case "July":
                            data[6].value = tempData[i].total_cost
                            break;
                        case "August":
                            data[7].value = tempData[i].total_cost
                            break;
                        case "September":
                            data[8].value = tempData[i].total_cost
                            break;
                        case "October":
                            data[9].value = tempData[i].total_cost
                            break;
                        case "November":
                            data[10].value = tempData[i].total_cost
                            break;
                        case "December":
                            data[11].value = tempData[i].total_cost
                            break;
                    }
                }

                Chart.setOption(option);//重新渲染图表
            } else {
                // ElMessage.error("未能获取到支出数据")
            }
        })
        .catch(function (error) {
            // ElMessage.error("操作失败")
            console.log(error);
        });
}

/**
 * 各部门本月支出
 * @param {*} dom 
 * @param {*} chartname 
 * @param {*} chartdata 
 */
export function monthlyExpenditureOfEachDepartment(dom, chartname, chartdata) {
    var Chart = echarts.init(dom);
    let option = {
        dataset: {
            dimensions: ['department_name', 'cost'],
            source: chartdata
        },
        title: {
            text: chartname,
            left: 'center',
            textStyle: {
                color: 'white'
            }
        },
        tooltip: {
            trigger: 'axis',
            confine: true,
            formatter: function (params) {
                return params[0].value.department_name + ':' + params[0].value.cost
            },
            renderMode: 'richText',//否则显示会有bug
        },
        // legend: {
        //     orient: 'vertical',
        //     left: 'right',
        //     // right:'right',
        //     top: 'center',
        //     textStyle: {
        //         color: 'white'
        //     }
        // },
        grid: { // 让图表占满容器
            // top: "0px",
            left: 30,
            right: 45,
            bottom: 20,
            containLabel: true,
        },
        xAxis: {
            type: 'category',
            name: '部门',
            nameTextStyle: {
                color: 'white'
            },
            axisLine: {
                lineStyle: {
                    color: 'white'
                },
            },
        },
        yAxis: {
            type: 'value',
            name: "支出/元",
            nameTextStyle: {
                color: 'white'
            },
            axisLine: {
                lineStyle: {
                    color: 'white'
                },
            },
        },
        series: [
            {
                type: 'bar',
                // data: data,
                encode: {
                    x: 'department_name',
                    y: 'cost'
                },
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                },
            }
        ]
    }
    Chart.setOption(option)
}

/**
 * XX部门各种交通工具使用占比
 * @param {*} dom 
 * @param {*} chartname 
 * @param {*} departmentId 
 */
export function proportionOfTransportationModesInDepartments(dom, chartname, departmentId) {
    var Chart = echarts.init(dom);
    let data = [];
    // 绘制图表
    let option = {
        title: {
            text: chartname,
            left: 'center',
            textStyle: {
                color: 'white'
            }
        },
        tooltip: {
            trigger: 'item',
            confine: true,
            // formatter: '{b}',
            renderMode: 'richText',//否则显示会有bug
        },
        legend: {
            orient: 'vertical',
            left: 'right',
            // right:'right',
            top: 'center',
            textStyle: {
                color: 'white'
            }
        },
        grid: { // 让图表占满容器
            top: "0px",
            left: "0px",
            right: "0px",
            bottom: "0px",
            containLabel: true,
        },

        series: [
            {
                type: 'pie',
                radius: '70%',
                data: data,
                // data:[{
                //     value:200,name:'ddd',
                // }],
                center: ['40%', '50%'],
                itemStyle: {
                    shadowBlur: 0,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                },
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 10,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                },
                label: {
                    // show: true,
                    position: 'inside',
                    color: 'rgba(255, 255, 255)',
                    formatter: '{d}%',
                    // formatter: function (e) {
                    //     console.log(e)
                    //     let data = e.data;
                    //     if (data.value == 0) {
                    //         return ''
                    //     } else {
                    //         return data.name + ':' + data.value + '人';
                    //     }
                    // },
                    textStyle: {
                        color: 'white',
                        //   fontSize: 20
                    }
                },
            }
        ]
    };
    // 渲染图表
    Chart.setOption(option);
    // 获取名数据
    axios({
        url: "/api/departmentStatistic/proportionOfTransportationModesInDepartments",
        method: "get",
        params: { "departmentId": departmentId },
        headers: {
            'Content-Type': 'application/json',
            "token": localStorage.getItem("token"),
        }
    })
        .then(function (response) {
            if (response.data.success === true && response.data.data != null) {
                for (let i = 0; i < response.data.data.length; i++) {
                    data.push({ value: response.data.data[i].value, name: response.data.data[i].vehicle })
                }
                Chart.setOption(option);//重新渲染图表
            } else {
                // ElMessage.error("未能获取到支出数据")
            }
        })
        .catch(function (error) {
            // ElMessage.error("操作失败")
            console.log(error);
        });
}

/**
 * 个人本年出差天数
 * @param {*} dom 
 * @param {*} chartname 
 * @param {*} userId 
 */
export function daysOfPersonalBusinessTripsThisYear(dom, chartname, userId) {
    var Chart = echarts.init(dom);
    let data = [
        { value: 0, name: '一月' },
        { value: 0, name: '二月' },
        { value: 0, name: '三月' },
        { value: 0, name: '四月' },
        { value: 0, name: '五月' },
        { value: 0, name: '六月' },
        { value: 0, name: '七月' },
        { value: 0, name: '八月' },
        { value: 0, name: '九月' },
        { value: 0, name: '十月' },
        { value: 0, name: '十一月' },
        { value: 0, name: '十二月' },
    ];
    let option = {
        dataset: {
            dimensions: ['name', 'value'],
            source: data
        },
        title: {
            text: chartname,
            left: 'center',
            textStyle: {
                color: 'white'
            }
        },
        tooltip: {
            trigger: 'none',
            axisPointer: {
                type: 'cross'
            }
        },
        // legend: {},
        grid: {
            top: 80,
            left: 30,
            right: 50,
            bottom: 50
        },
        xAxis:
        {
            type: 'category',
            name: "月份",
            nameTextStyle: {
                color: 'white'
            },
            axisLine: {
                lineStyle: {
                    color: 'white'
                },
            },
            axisTick: {
                alignWithLabel: true
            },
            // axisLine: {
            //     onZero: false,
            //     lineStyle: {
            //         // color:"green"
            //     }
            // },
            axisPointer: {
                label: {
                    formatter: function (params) {
                        return params.seriesData[0].data.name + ":" + params.seriesData[0].data.value + "天";
                    },
                    textStyle: {
                        color: 'black'
                    }
                }
            },
            // prettier-ignore
        },
        yAxis:
        {
            type: 'value',
            name: "天数",
            nameTextStyle: {
                color: 'white'
            },
            axisLine: {
                lineStyle: {
                    color: 'white'
                },
            },
            axisPointer: {
                label: {
                    textStyle: {
                        color: 'black'
                    }
                }
            },
        },
        series: [
            {
                type: 'line',
                smooth: true,
                emphasis: {
                    focus: 'series'
                },
                lineStyle: {
                    color: "#51A7FE"
                }
                // data: data,
            }
        ]
    };
    Chart.setOption(option)
    // 获取名数据
    axios({
        url: "/api/personalStatisticController/daysOfPersonalBusinessTripsThisYear",
        method: "get",
        params: { "user_id": userId },
        headers: {
            'Content-Type': 'application/json',
            "token": localStorage.getItem("token"),
        }
    })
        .then(function (response) {
            if (response.data.success === true && response.data.data != null) {
                var tempData = response.data.data
                for (var i = 0; tempData != null && i < tempData.length; i++) {
                    // January, February, March, April, May, June, July, August, September, October, November, December
                    switch (tempData[i].month) {
                        case "January":
                            data[0].value = tempData[i].days
                            break;
                        case "February":
                            data[1].value = tempData[i].days
                            break;
                        case "March":
                            data[2].value = tempData[i].days
                            break;
                        case "April":
                            data[3].value = tempData[i].days
                            break;
                        case "May":
                            data[4].value = tempData[i].days
                            break;
                        case "June":
                            data[5].value = tempData[i].days
                            break;
                        case "July":
                            data[6].value = tempData[i].days
                            break;
                        case "August":
                            data[7].value = tempData[i].days
                            break;
                        case "September":
                            data[8].value = tempData[i].days
                            break;
                        case "October":
                            data[9].value = tempData[i].days
                            break;
                        case "November":
                            data[10].value = tempData[i].days
                            break;
                        case "December":
                            data[11].value = tempData[i].days
                            break;
                    }
                }
                Chart.setOption(option);//重新渲染图表
            } else {
                // ElMessage.error("未能获取到支出数据")
            }
        })
        .catch(function (error) {
            // ElMessage.error("操作失败")
            console.log(error);
        });
}

/**
 * 个人本年支出类型占比
 * @param {*} dom 
 * @param {*} chartname 
 * @param {*} userId 
 */
export function proportionOfPersonalExpenditureTypes(dom, chartname, userId) {
    var Chart = echarts.init(dom);
    let data = [];
    // 绘制图表
    let option = {
        title: {
            text: chartname,
            left: 'center',
            textStyle: {
                color: 'white'
            }
        },
        tooltip: {
            trigger: 'item',
            confine: true,
            // formatter: '{b}',
            renderMode: 'richText',//否则显示会有bug
        },
        legend: {
            orient: 'vertical',
            left: 'right',
            // right:'right',
            top: 'center',
            textStyle: {
                color: 'white'
            }
        },
        grid: { // 让图表占满容器
            top: "0px",
            left: "0px",
            right: "0px",
            bottom: "0px",
            containLabel: true,
        },

        series: [
            {
                type: 'pie',
                radius: '65%',
                data: data,
                // data:[{
                //     value:200,name:'ddd',
                // }],
                center: ['40%', '50%'],
                itemStyle: {
                    shadowBlur: 0,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                },
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 10,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                },
                label: {
                    // show: true,
                    position: 'inside',
                    color: 'rgba(255, 255, 255)',
                    formatter: '{d}%',
                    // formatter: function (e) {
                    //     console.log(e)
                    //     let data = e.data;
                    //     if (data.value == 0) {
                    //         return ''
                    //     } else {
                    //         return data.name + ':' + data.value + '人';
                    //     }
                    // },
                    textStyle: {
                        color: 'white',
                        //   fontSize: 20
                    }
                },
            }
        ]
    };
    // 渲染图表
    Chart.setOption(option);
    // 获取名数据
    axios({
        url: "/api/personalStatisticController/proportionOfPersonalExpenditureTypes",
        method: "get",
        params: { "user_id": userId },
        headers: {
            'Content-Type': 'application/json',
            "token": localStorage.getItem("token"),
        }
    })
        .then(function (response) {
            if (response.data.success === true && response.data.data != null) {
                data.push({ value: response.data.data.transportation_expense, name: "交通费" })
                data.push({ value: response.data.data.hotel_expense, name: "住宿费" })
                data.push({ value: response.data.data.catering_expenses, name: "餐饮费" })
                data.push({ value: response.data.data.other_expenses, name: "其他费用" })
                Chart.setOption(option);//重新渲染图表
            } else {
                // ElMessage.error("未能获取到支出数据")
            }
        })
        .catch(function (error) {
            // ElMessage.error("操作失败")
            console.log(error);
        });
}

/**
 * 个人年度出差城市统计
 * @param {*} dom 
 * @param {*} chartname 
 * @param {*} userId 
 */
export function businessTripCityStatistics(dom, chartname, userId) {
    var Chart = echarts.init(dom);
    let data = [];
    // 绘制图表
    let option = {
        title: {
            text: chartname,
            left: 'center',
            textStyle: {
                color: 'white'
            }
        },
        tooltip: {
            trigger: 'item',
            confine: true,
            // formatter: '{b}',
            renderMode: 'richText',//否则显示会有bug
        },
        legend: {
            orient: 'vertical',
            left: 'right',
            // right:'right',
            top: 'center',
            textStyle: {
                color: 'white'
            }
        },
        grid: { // 让图表占满容器
            top: 20,
            left: 20,
            right: 20,
            bottom: 20,
            containLabel: true,
        },

        series: [
            {
                type: 'pie',
                radius: '65%',
                data: data,
                center: ['40%', '50%'],
                itemStyle: {
                    shadowBlur: 0,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                },
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 10,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                },
                label: {
                    // show: true,
                    position: 'inside',
                    color: 'rgba(255, 255, 255)',
                    formatter: '{b}',
                    // formatter: function (e) {
                    //     console.log(e)
                    //     let data = e.data;
                    //     if (data.value == 0) {
                    //         return ''
                    //     } else {
                    //         return data.name + ':' + data.value + '人';
                    //     }
                    // },
                    textStyle: {
                        color: 'white',
                        //   fontSize: 20
                    }
                },
            }
        ]
    };
    // 渲染图表
    Chart.setOption(option);
    // 获取名数据
    axios({
        url: "/api/personalStatisticController/businessTripCityStatistics",
        method: "get",
        params: { "user_id": userId },
        headers: {
            'Content-Type': 'application/json',
            "token": localStorage.getItem("token"),
        }
    })
        .then(function (response) {
            if (response.data.success === true) {
                for (let i = 0; i < response.data.data.length; i++) {
                    data.push(
                        { name: response.data.data[i].city, value: response.data.data[i].value}
                    )
                }
                Chart.setOption(option);//重新渲染图表
            } else {
                ElMessage.error("未能获取到出差城市数据")
            }
        })
        .catch(function (error) {
            // ElMessage.error("操作失败")
            console.log(error);
        });
}
