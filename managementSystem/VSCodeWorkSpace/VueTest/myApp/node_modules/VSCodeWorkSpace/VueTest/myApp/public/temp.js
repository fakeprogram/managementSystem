// 图一
option = {
  backgroundColor: 'rgba(66, 59, 59, 0.8)',
  title: {
    text: '任务类型占比',
    left: 'center',
    top: 20,
    textStyle: {
      color: 'white',
      fontSize: 30
    }
  },
  legend: {
    orient: 'vertical',
    left: 'right',
    top: '20%',
    itemGap: 30,
    textStyle: {
      color: 'white',
      fontSize: 20
    }
  },
  tooltip: {
    trigger: 'item'
  },
  // visualMap: {
  //   show: false,
  //   min: 80,
  //   max: 600,
  //   inRange: {
  //     colorLightness: [0, 1]
  //   }
  // },
  series: [
    {
      type: 'pie',
      radius: '60%',
      center: ['50%', '40%'],
      data: [
        { value: 335, name: '采购物品' },
        { value: 310, name: '开发市场' },
        { value: 274, name: '洽谈商务' },
        { value: 235, name: '交流学习' },
        { value: 400, name: '系统维护' }
      ].sort(function (a, b) {
        return a.value - b.value;
      }),
      // roseType: 'radius',
      label: {
        // show:false,
        position: 'inside',
        color: 'rgba(255, 255, 255)',
        formatter: '{b}:{d}%'
      },
      labelLine: {
        lineStyle: {
          color: 'rgba(255, 255, 255, 0.3)'
        },
        smooth: 0.2,
        length: 10,
        length2: 20
      },
      // itemStyle: {
      //   color: '#c23531',
      //   shadowBlur: 200,
      //   shadowColor: 'rgba(0, 0, 0, 0.5)'
      // },
      animationType: 'scale',
      animationEasing: 'elasticOut',
      animationDelay: function (idx) {
        return Math.random() * 200;
      }
    }
  ]
};

//图二
option = {
  backgroundColor: 'rgba(66, 59, 59, 0.8)',
  title: {
    text: '各地人数',
    left: 'center',
    top: 20,
    textStyle: {
      color: 'white',
      fontSize: 30
    }
  },
  textStyle: {
    color: 'white'
  },
  xAxis: {
    type: 'category',
    data: ['北京', '成都', '上海', '贵阳', '南京', '天津', '武汉'],
    name: '城市'
  },
  yAxis: {
    type: 'value',
    name: '人数'
  },
  series: [
    {
      data: [5, 6, 5, 4, 7, 3, 5],
      type: 'bar'
    }
  ]
};

//图三
option = {
  backgroundColor: 'rgba(66, 59, 59, 0.8)',
  title: {
    text: '采购部本月支出类型占比',
    left: 'center',
    top: 20,
    textStyle: {
      color: 'white',
      fontSize: 30
    }
  },
  legend: {
    orient: 'vertical',
    left: 'right',
    top: 'center',
    itemGap: 30,
    textStyle: {
      color: 'white',
      fontSize: 20
    }
  },
  tooltip: {
    trigger: 'item'
  },
  // visualMap: {
  //   show: false,
  //   min: 80,
  //   max: 600,
  //   inRange: {
  //     colorLightness: [0, 1]
  //   }
  // },
  series: [
    {
      type: 'pie',
      radius: '60%',
      center: ['50%', '40%'],
      data: [
        { value: 20000, name: '交通' },
        { value: 13000, name: '住宿' },
        { value: 10000, name: '餐饮' },
        { value: 5000, name: '其他' },
      ].sort(function (a, b) {
        return a.value - b.value;
      }),
      // roseType: 'radius',
      label: {
        // show:false,
        position: 'inside',
        color: 'rgba(255, 255, 255)',
        formatter: '{b}:{d}%',
        textStyle: {
          color: 'white',
          fontSize: 20
        }
      },
      labelLine: {
        lineStyle: {
          color: 'rgba(255, 255, 255, 0.3)'
        },
        smooth: 0.2,
        length: 10,
        length2: 20
      },
      // itemStyle: {
      //   color: '#c23531',
      //   shadowBlur: 200,
      //   shadowColor: 'rgba(0, 0, 0, 0.5)'
      // },
      animationType: 'scale',
      animationEasing: 'elasticOut',
      animationDelay: function (idx) {
        return Math.random() * 200;
      }
    }
  ]
};

//图四
option = {
  backgroundColor: 'rgba(66, 59, 59, 0.8)',
  title: {
    text: '采购部本年出差支出统计',
    left: 'center',
    top: 20,
    textStyle: {
      color: 'white',
      fontSize: 30
    }
  },
  textStyle: {
    color: 'white'
  },
  xAxis: {
    type: 'category',
    data: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
    name: '月份'
  },
  yAxis: {
    type: 'value',
    name: '金额/元'
  },
  series: [
    {
      data: [50000, 60000, 55000, 48000, 70000, 53000, 52000, 47000, 50000, 66000, 54000, 57000],
      type: 'bar'
    }
  ]
};
//各部门本月支出
option = {
  backgroundColor: 'rgba(66, 59, 59, 0.8)',
  title: {
    text: '各部门本月出差支出统计',
    left: 'center',
    top: 20,
    textStyle: {
      color: 'white',
      fontSize: 30
    }
  },
  textStyle: {
    color: 'white'
  },
  xAxis: {
    type: 'category',
    data: ['采购部', '市场部', '财务部', '人事部', '技术部', '项目部'],
    name: '部门'
  },
  yAxis: {
    type: 'value',
    name: '金额/元'
  },
  series: [
    {
      data: [50000, 60000, 55000, 48000, 70000, 53000],
      type: 'bar'
    }
  ]
};

//采购部交通方式占比
option = {
  backgroundColor: 'rgba(66, 59, 59, 0.8)',
  title: {
    text: '采购部本月交通方式占比',
    left: 'center',
    top: 20,
    textStyle: {
      color: 'white',
      fontSize: 30
    }
  },
  legend: {
    orient: 'vertical',
    left: 'right',
    top: 'center',
    itemGap: 30,
    textStyle: {
      color: 'white',
      fontSize: 20
    }
  },
  tooltip: {
    trigger: 'item'
  },
  // visualMap: {
  //   show: false,
  //   min: 80,
  //   max: 600,
  //   inRange: {
  //     colorLightness: [0, 1]
  //   }
  // },
  series: [
    {
      type: 'pie',
      radius: '60%',
      center: ['50%', '40%'],
      data: [
        { value: 20, name: '飞机' },
        { value: 13, name: '高铁' },
        { value: 5, name: '客车' },
        { value: 15, name: '自驾' },
      ].sort(function (a, b) {
        return a.value - b.value;
      }),
      // roseType: 'radius',
      label: {
        // show:false,
        position: 'inside',
        color: 'rgba(255, 255, 255)',
        formatter: '{c}次',
        textStyle: {
          color: 'white',
          fontSize: 20
        }
      },
      labelLine: {
        lineStyle: {
          color: 'rgba(255, 255, 255, 0.3)'
        },
        smooth: 0.2,
        length: 10,
        length2: 20
      },
      // itemStyle: {
      //   color: '#c23531',
      //   shadowBlur: 200,
      //   shadowColor: 'rgba(0, 0, 0, 0.5)'
      // },
      animationType: 'scale',
      animationEasing: 'elasticOut',
      animationDelay: function (idx) {
        return Math.random() * 200;
      }
    }
  ]
};
// 诸葛亮支出占比
option = {
  backgroundColor: 'rgba(66, 59, 59, 0.8)',
  title: {
    text: '诸葛亮本年出差支出类型占比',
    left: 'center',
    top: 20,
    textStyle: {
      color: 'white',
      fontSize: 30
    }
  },
  legend: {
    orient: 'vertical',
    left: 'right',
    top: 'center',
    itemGap: 30,
    textStyle: {
      color: 'white',
      fontSize: 20
    }
  },
  tooltip: {
    trigger: 'item'
  },
  // visualMap: {
  //   show: false,
  //   min: 80,
  //   max: 600,
  //   inRange: {
  //     colorLightness: [0, 1]
  //   }
  // },
  series: [
    {
      type: 'pie',
      radius: '60%',
      center: ['50%', '40%'],
      data: [
        { value: 8000, name: '交通费' },
        { value: 7500, name: '住宿费' },
        { value: 5000, name: '餐饮费' },
        { value: 3000, name: '其他费用' },
      ].sort(function (a, b) {
        return a.value - b.value;
      }),
      // roseType: 'radius',
      label: {
        // show:false,
        position: 'inside',
        color: 'rgba(255, 255, 255)',
        formatter: '{c}元',
        textStyle: {
          color: 'white',
          fontSize: 20
        }
      },
      labelLine: {
        lineStyle: {
          color: 'rgba(255, 255, 255, 0.3)'
        },
        smooth: 0.2,
        length: 10,
        length2: 20
      },
      // itemStyle: {
      //   color: '#c23531',
      //   shadowBlur: 200,
      //   shadowColor: 'rgba(0, 0, 0, 0.5)'
      // },
      animationType: 'scale',
      animationEasing: 'elasticOut',
      animationDelay: function (idx) {
        return Math.random() * 200;
      }
    }
  ]
};

//   诸葛亮出地点统计
option = {
  backgroundColor: 'rgba(66, 59, 59, 0.8)',
  title: {
    text: '诸葛亮本年出差地点统计',
    left: 'center',
    top: 20,
    textStyle: {
      color: 'white',
      fontSize: 30
    }
  },
  legend: {
    orient: 'vertical',
    left: 'right',
    top: 'center',
    itemGap: 30,
    textStyle: {
      color: 'white',
      fontSize: 20
    }
  },
  tooltip: {
    trigger: 'item'
  },
  // visualMap: {
  //   show: false,
  //   min: 80,
  //   max: 600,
  //   inRange: {
  //     colorLightness: [0, 1]
  //   }
  // },
  series: [
    {
      type: 'pie',
      radius: '60%',
      center: ['50%', '40%'],
      data: [
        { value: 3, name: '北京' },
        { value: 5, name: '上海' },
        { value: 2, name: '天津' },
        { value: 1, name: '杭州' },
        { value: 3, name: '成都' },
        { value: 4, name: '贵阳' },
      ].sort(function (a, b) {
        return a.value - b.value;
      }),
      // roseType: 'radius',
      label: {
        // show:false,
        position: 'inside',
        color: 'rgba(255, 255, 255)',
        formatter: '{c}次',
        textStyle: {
          color: 'white',
          fontSize: 20
        }
      },
      labelLine: {
        lineStyle: {
          color: 'rgba(255, 255, 255, 0.3)'
        },
        smooth: 0.2,
        length: 10,
        length2: 20
      },
      // itemStyle: {
      //   color: '#c23531',
      //   shadowBlur: 200,
      //   shadowColor: 'rgba(0, 0, 0, 0.5)'
      // },
      animationType: 'scale',
      animationEasing: 'elasticOut',
      animationDelay: function (idx) {
        return Math.random() * 200;
      }
    }
  ]
};

//   诸葛亮出差天数统计
option = {
  backgroundColor: 'rgba(66, 59, 59, 0.8)',
  title: {
    text: '诸葛亮本年出差天数统计',
    left: 'center',
    top: 20,
    textStyle: {
      color: 'white',
      fontSize: 30
    }
  },
  textStyle: {
    color: 'white'
  },
  xAxis: {
    type: 'category',
    data: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
    name: '月份'
  },
  yAxis: {
    type: 'value',
    name: '天'
  },
  series: [
    {
      data: [10, 11, 5, 7, 6, 5, 5, 9, 8, 7, 2, 6],
      type: 'bar'
    }
  ]
};


/**
 * 本年个人出差天数排名
 */
option = {
  dataset: {
    dimensions: ['user_name', 'alldays'],
    source: [
      {
        "alldays": 14,
        "user_name": "张三"
      },
      {
        "alldays": 3,
        "user_name": "赵六"
      },
      {
        "alldays": 3,
        "user_name": "孙十二"
      },
      {
        "alldays": 1,
        "user_name": "李四"
      }
    ],
  },
  xAxis: {
    type: 'category',
    name: '名称'
  },
  yAxis: {
    type: 'value',
    name: "出差天数"
  },
  series: [
    {
      type: 'bar'
    }
  ]
};