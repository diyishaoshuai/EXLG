<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>员工管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 搜索筛选 -->
    <el-form :inline="true" class="user-search">
      <el-form-item label="搜索：">
        <el-input
          size="small"
          placeholder="请输入查询信息"
          v-model="searchKey"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-select size="small" v-model="type" placeholder="请选择职位">
          <el-option
            label="全部"
            value="-1"
            @click.native="getdata()"
          ></el-option>
          <el-option
            v-for="(item, index) in this.types"
            :label="item"
            :value="index"
            :key="index"
            @click.native="selectJob(index)"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button
          size="small"
          type="primary"
          icon="el-icon-search"
          @click="search"
          >搜索</el-button
        >
        <el-button
          size="small"
          type="primary"
          icon="el-icon-plus"
          @click="showEmp()"
          >添加</el-button
        >
      </el-form-item>
    </el-form>
    <!--列表-->
    <el-table
      size="small"
      :data="currListData"
      highlight-current-row
      v-loading="loading"
      border
      element-loading-text="拼命加载中"
      style="width: 100%"
    >
      <el-table-column align="center" type="selection" width="60">
      </el-table-column>
      <el-table-column sortable prop="name" label="姓名" width="150">
      </el-table-column>
      <el-table-column sortable prop="sex" label="性别" width="150">
        <template slot-scope="scope">
          <span v-if="scope.row.sex == 0">女</span>
          <span v-if="scope.row.sex == 1">男</span>
        </template>
      </el-table-column>
      <el-table-column sortable prop="age" label="年龄" width="150">
      </el-table-column>
      <el-table-column sortable prop="telephone" label="联系电话" width="150">
      </el-table-column>
      <el-table-column sortable prop="email" label="电子邮箱" width="150">
      </el-table-column>
      <el-table-column sortable prop="job" label="职位" width="150">
        <template slot-scope="scope">
          <span v-if="scope.row.job == 0">负责人</span>
          <span v-if="scope.row.job == 1">普通员工</span>
          <span v-if="scope.row.job == 2">司机</span>
        </template>
      </el-table-column>
      <el-table-column sortable prop="address" label="家庭住址" width="150">
      </el-table-column>

      <el-table-column align="center" label="修改" min-width="300">
        <template slot-scope="scope">
          <el-button size="mini" icon="el-icon-edit" @click="showEmp(scope.$index, scope.row)"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="danger"
            @click="deleteEmp(scope.$index, scope.row)"
            icon="el-icon-delete"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <Pagination
      v-bind:child-msg="pageparm"
      @callFather="callFather"
    ></Pagination>
    <!-- 编辑界面 -->
    <el-dialog :title="title" :visible.sync="editFormVisible" width="35%">
      <el-form label-width="120px" :model="oneEmpData">
        <el-form-item label="姓名">
          <el-input size="small" v-model.trim="oneEmpData.name"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="oneEmpData.sex" placeholder="请选择性别">
            <el-option label="男" :value="1"></el-option>
            <el-option label="女" :value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input
            size="small"
            v-model.trim.number="oneEmpData.age"
          ></el-input>
        </el-form-item>
        <el-form-item label="职位">
          <el-select v-model="oneEmpData.job" placeholder="请选择职位">
            <el-option label="负责人" :value="0"></el-option>
            <el-option label="普通员工" :value="1"></el-option>
            <el-option label="司机" :value="2"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="电子邮箱">
          <el-input size="small" v-model.trim="oneEmpData.email"></el-input>
        </el-form-item>
        <el-form-item label="联系电话">
          <el-input size="small" v-model.trim="oneEmpData.telephone"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" @click="closeDialog">取消</el-button>
        <el-button
          size="small"
          type="primary"
          :loading="loading"
          class="title"
          @click="submitForm"
          >保存</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>

<script>
import Axios from "axios";
import Pagination from "../../components/Pagination";
const baseUrl = "http://localhost:8888";
export default {
  data() {
    return {
      loading: false, //是显示加载
      editFormVisible: false, //控制编辑页面显示与隐藏
      title: "添加",
      searchKey: "",
      AllListData: [],
      currListData: [], //用户数据
      oneEmpData: {},
      // 分页参数
      pageparm: {
        currentPage: 1,
        pageSize: 10,
        totalCount: 10,
        totalPage: 0,
      },
      index: 0,
      //类型数组
      types: [],
      type: "",
    };
  },
  // 注册组件
  components: {
    Pagination,
  },
  /**
   * 创建完毕
   */
  created() {
    this.getdata();
  },
  methods: {
    selectJob(index) {
      Axios({
        method: "get",
        url: `${baseUrl}/employee/listByPart`,
        params: {
          key: this.searchKey,
          job: index,
        },
      }).then((res) => {
        this.$nextTick(() => {
          this.pageparm.totalCount = res.data.page.totalCount;
          this.pageparm.totalPage = res.data.page.totalPage;
          this.AllListData = res.data.page.list;
          //初始化分页
          this.setPaginations();
        });
      });
    },
    // 搜索事件
    search() {
      Axios({
        method: "get",
        url: `${baseUrl}/employee/listByPart`,
        params: {
          key: this.searchKey,
        },
      }).then((res) => {
        console.log(res.data);
        this.$nextTick(() => {
          this.pageparm.totalCount = res.data.page.totalCount;
          this.pageparm.totalPage = res.data.page.totalPage;
          this.AllListData = res.data.page.list;
          //初始化分页
          this.setPaginations();
        });
      });
    },

    //找出所有类型
    getTypes() {
      Axios({
        method: "get",
        url: `${baseUrl}/employee/getAllEmpJob`,
      }).then((res) => {
        this.types = res.data.list;
        console.log(this.types);
      });
    },
    // 获取所有员工信息
    getdata() {
      this.loading = true;
      Axios({
        method: "get",
        url: `${baseUrl}/employee/listByPart`,
      }).then((res) => {
        this.loading = false;
        this.pageparm.totalCount = res.data.page.totalCount;
        this.pageparm.totalPage = res.data.page.totalPage;
        this.AllListData = res.data.page.list;
        //初始化分页
        this.setPaginations();
        //获取所有职位
        this.getTypes();
      });
    },
    //初始化分页
    setPaginations() {
      this.pageparm.currentPage = 1; //默认显示第一页
      this.pageparm.pageSize = 10; //默认大小10
      //显示数据
      this.currListData = this.AllListData.filter((item, index) => {
        return index < this.pageparm.pageSize;
      });
      console.log(this.currListData);
    },
    // 分页插件事件
    callFather(parm) {
      if (parm.pageSize) {
        //处理页面大小
        this.pageparm.currentPage = 1;
        this.pageparm.pageSize = parm.pageSize;
        this.currListData = this.AllListData.filter((item, index) => {
          return index < this.pageparm.pageSize;
        });
      }
      if (parm.currentPage) {
        //处理页码改变
        //获取当前页的第一个元素的索引
        let index = this.pageparm.pageSize * (parm.currentPage - 1);
        let currAllData = this.pageparm.pageSize * parm.currentPage; //当前所有数据总数
        let tablist = [];
        // let allData = this.AllTableData.list; //所有数据
        for (let i = index; i < currAllData; i++) {
          if (this.AllListData[i]) {
            tablist.push(this.AllListData[i]);
          }
        }
        this.currListData = tablist;
      }
    },
    // 搜索事件
    search() {
      // this.getdata(this.formInline);
    },
    //显示编辑界面(新增和修改一起)
    handleEdit: function (index, row) {
      this.editFormVisible = true;
      this.getTypes();
      this.title = "添加";
    },
    // 编辑、增加页面保存方法
    submitForm() {
      Axios({
        method: "post",
        url: `${baseUrl}/employee/saveEmpByOne`,
        data: this.oneEmpData,
      }).then((res) => {
        //关闭模态框
        this.editFormVisible = false;
        //重新获取数据
        this.getdata();
        //重置表单数据
        this.oneEmpData = {};
        this.$message({
          type: "success",
          message: "保存成功",
        });
      });
    },
    // 删除员工信息
    deleteEmp(index, row) {
      this.$confirm("确定要删除吗?", "信息", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          Axios({
            method: "delete",
            url: `${baseUrl}/employee/deleteById/` + row.id,
          })
            .then((res) => {
              //更新数据
              this.getdata();
              if (res.data.code === 0) {
                this.$message({
                  type: "success",
                  message: "员工已删除!",
                });
              } else {
                this.$message({
                  type: "info",
                  message: res.data.msg,
                });
              }
            })
            .catch((err) => {
              this.loading = false;
              this.$message.error("员工删除失败，请稍后再试！");
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    //显示员工信息
    showEmp(index, row) {
      if (index == null) {
        this.title = "添加";
        this.oneEmpData = {};
        this.editFormVisible = true;
      } else {
        this.title = "修改";
        Axios({
          method: "get",
          url: `${baseUrl}/employee/getEmpById/` + row.id,
        }).then((res) => {
          this.oneEmpData = res.data.emp;
          console.log(this.oneEmpData);
          this.editFormVisible = true;
        });
      }
    },

    //打开编辑、增加弹出框
    openDialog() {
      this.editFormVisible = true;
    },
    // 关闭编辑、增加弹出框
    closeDialog() {
      this.editFormVisible = false;
    },
  },
  watch: {
    //监视搜索关键字,一旦关键字发送改变,就去数据库中查询相应的信息
    searchKey(newValue, oldValue) {
      Axios({
        method: "get",
        url: `${baseUrl}/employee/listByPart`,
        params: {
          key: newValue,
        },
      }).then((res) => {
        console.log(res.data);
        this.$nextTick(() => {
          this.pageparm.totalCount = res.data.page.totalCount;
          this.pageparm.totalPage = res.data.page.totalPage;
          this.AllListData = res.data.page.list;
          //初始化分页
          this.setPaginations();
        });
      });
    },
  },
};
</script>

<style scoped>
.user-search {
  margin-top: 20px;
}
.userRole {
  width: 100%;
}
</style>

 
 