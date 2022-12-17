/**
* 头部菜单
*/ 
<template>
  <el-menu
    class="el-menu-demo"
    mode="horizontal"
    background-color="#334157"
    text-color="#fff"
    active-text-color="#fff"
  >
    <el-button class="buttonimg">
      <img
        class="showimg"
        :src="collapsed ? imgsq : imgshow"
        @click="toggle(collapsed)"
      />
    </el-button>
    <el-submenu index="2" class="submenu">
      <template slot="title">{{ name }}</template>
      <el-menu-item index="2-1">设置</el-menu-item>
      <el-menu-item
        @click="$router.push({ path: '/person/update' })"
        index="2-2"
        >个人中心</el-menu-item
      >
      <el-menu-item @click="exit()" index="2-3">退出</el-menu-item>
    </el-submenu>
    <el-avatar size="large" class="avatar" :src="avatarUrl"></el-avatar>
  </el-menu>
</template>
<script>
import Axios from "axios";
const baseUrl = "http://localhost:8888";
export default {
  name: "navcon",
  data() {
    return {
      emp: {},
      avatarUrl: "",
      name: "",
      collapsed: true,
      imgshow: require("../assets/img/show.png"),
      imgsq: require("../assets/img/sq.png"),
      user: {},
    };
  },
  // 创建完毕状态(里面是操作)
  created() {
    // let emp = this.$store.getters.getEmp;
    console.log(JSON.parse(localStorage.getItem("userInfo")))
    this.emp = JSON.parse(localStorage.getItem("userInfo"));
    this.name = this.emp.name;
    this.avatarUrl = this.emp.avatar;
    console.log(this.emp);
  },
  methods: {
    // 退出登录
    exit() {
      this.$confirm("退出登录, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          // 发送axios请求去后台将redis中数据删除
          Axios({
            method: "get",
            url: `${baseUrl}/employee/logout`,
          }).then((res) => {
            this.$store.commit("logout", "false");
            this.$router.push({ path: "/login" });
            this.$message({
              type: "success",
              message: "已退出登录!",
            });
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消",
          });
        });
    },
    // 切换显示
    toggle(showtype) {
      this.collapsed = !showtype;
      this.$root.Bus.$emit("toggle", this.collapsed); 
    },
  },
  mounted() {
    this.$bus.$on("updateUserInfo", (data) => {
      this.emp = JSON.parse(localStorage.getItem("userInfo"));
      // this.$forceupdate();
      // this.$nextTick()
      // this.$set(this.emp,emp.avatar,)
    });
    this.$forceUpdate()
    // console.log(this.emp);
    // this.$forceupdate();
  },
  // watch: {
  //   emp(val){

  //   }
  // }
  // beforeUpdate() {
  //   this.emp = JSON.parse(localStorage.getItem("userInfo"));
  //   this.name = this.emp.name;
  //   this.avatarUrl = this.emp.avatar;
  // },
};
</script>
<style scoped>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  border: none;
}
.submenu {
  float: right;
}
.buttonimg {
  height: 60px;
  background-color: transparent;
  border: none;
}
.showimg {
  width: 26px;
  height: 26px;
  position: absolute;
  top: 17px;
  left: 17px;
}
.showimg:active {
  border: none;
}
.avatar {
  float: right;
  margin-top: 10px;
}
</style>