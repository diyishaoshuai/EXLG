<template>
  <div>
    <el-button
      type="primary"
      @click="$router.push({ path: '/charts/statistics' })"
      ><i class="el-icon-arrow-left el-icon--left"> 返回</i></el-button
    >
    <h2>个人中心</h2>
    <el-upload
      class="avatar-uploader"
      accept
      action
      :data="policy"
      :show-file-list="false"
      :on-success="handleAvatarSuccess"
      :before-upload="beforeAvatarUpload"
      :http-request="handleUpload"
    >
      <img v-if="imageUrl" :src="imageUrl" class="avatar" />
      <i v-else class="el-icon-user avatar-uploader-icon"></i>
    </el-upload>
    <div class="myform">
      <el-form ref="form" :model="form" label-width="80px" :rules="rules">
        <el-form-item label="昵称" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input v-model="form.age"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="form.sex">
            <el-radio :label="1">男</el-radio>
            <el-radio :label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="联系电话" prop="telephone">
          <el-input v-model="form.telephone"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit()">保存</el-button>
          <el-button>取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import Axios from "axios";
Axios.defaults.withCredentials = true;
const baseUrl = "http://localhost:8888";
export default {
  data() {
    return {
      emp: {},
      file: {},
      policy: {},
      imageUrl: "",
      rules: {
        name: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          { min: 1, max: 6, message: "长度在 1 到 6 个字符", trigger: "blur" },
        ],
        age: [
          { required: true, message: "请输入年龄", trigger: "blur" },
          { min: 1, max: 3, message: "请输入正确的年龄", trigger: "blur" },
        ],
        telephone: [
          { required: true, message: "请输入联系电话", trigger: "blur" },
          {
            required: true,
            len: 11,
            message: "请输入正确的联系电话",
            trigger: "blur",
          },
          {
            required: true,
            type: "number",
            message: "请输入数字",
            trigger: "blur",
          },
        ],
        email: [
          { required: true, message: "请输入邮箱", trigger: "blur" },
          {
            type: "email",
            message: "请输入正确的邮箱",
            trigger: "blur",
          },
        ],
      },
    };
  },
  created() {
    //创建组件时,去vuex中获取员工的信息
    // let emp = this.$store.getters.getEmp;
    this.emp = JSON.parse(localStorage.getItem('userInfo'));
    this.form = this.emp;
    this.imageUrl = this.emp.avatar;
  },
  methods: {
    // 上传成功之后  提示用户
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
      // localStorage.setItem('userInfo', JSON.stringify());
      // console.log(this.imageUrl);
    },
    // 头像上传之前, 去后端拿签名和url
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      this.file = file;
      return isJPG && isLt2M;
    },
    // 自定义上传
    handleUpload() {
      //发axios请求去后端拿签名和url
      console.log(this.file);
      const formdata = new FormData();
      formdata.append("file", this.file);
      Axios({
        method: "post",
        url: `${baseUrl}/oss/policy`,
        headers: {
          "Content-Type": "multipart/form-data",
        },
        data: formdata,
      }).then((res) => {
        if (res.data.code === 0) {
          // 重新设置本地存储中的值
          localStorage.setItem('userInfo', JSON.stringify(res.data.userInfo));
          this.$bus.$emit('updateUserInfo',res.data.userInfo)
          this.imageUrl = res.data.userInfo.avatar;
          this.$message({
            message: '上传成功',
            type: "success",
          });
        }
      });
    },

    onSubmit() {
      let employee = this.form;
      Axios({
        method: "put",
        url: `${baseUrl}/employee/update`, //员工修改的请求
        data: {
          id: employee.id,
          name: employee.name,
          age: employee.age,
          sex: employee.sex,
          telephone: employee.telephone,
          email: employee.email,
        },
      }).then((res) => {
        //数据重置
        if (res.data.code == 0) {
          //修改成功，返回到图表界面
          this.$message.success("修改成功");
          this.$router.push({ path: "/charts/statistics" });
        } else {
          //修改失败, 提示错误信息
          this.$message.error(res.data.error);
        }
      });
    },
  },
};
</script>

<style scoped>
.myform {
  width: 500px;
  /* position: relative;
  left: 100px;
  top: -20px; */
  margin: 0 auto;
  /* margin-top: 75px; */
}
h2 {
  /* position: relative;
  top: -30px;
  left: 10px; */
  margin-left: 50%;
}

.avatar-uploader {
  border: 1px dashed #d9d9d9;
  border-radius: 50%;
  cursor: pointer;
  margin: 40px 50%;
  height: 100px;
  width: 100px;
  position: relative;
  overflow: hidden;
}
.avatar-uploader:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 100px;
  color: #8c939d;
  width: 100px;
  height: 100px;
  line-height: 100px;
  text-align: center;
}
.avatar {
  border-radius: 50%;
  width: 100px;
  height: 100px;
  display: block;
}
</style>