<template>
  <div>
    <el-container>
      <el-header>欢迎使用AI Auto Design</el-header>
      <!-- <el-main style="height: 70%;">

        <h1 style="height: 20px; float: top;">AI Auto Design</h1>

        <el-row>
          <el-col :span="20">
            <div class="grid-content bg-purple">
              <div style="margin-top: 15px;">

                <div style="margin-top: 15px;">
                  <el-input placeholder="请输入内容" v-model="input3" class="input-with-select">
                  </el-input>
                </div>

              </div>
            </div>

          </el-col>
          <el-col :span="4">
            <div class="grid-content bg-purple-light">
              <div style="margin-top: 15px;">
                <el-button type="primary">生成图片</el-button>
              </div>
            </div>
          </el-col>
        </el-row>


        <el-result title="AI 生成的图片">
          <template slot="icon">
            <div class="demo-image__placeholder" style="width: auto; height: ;">
              <div class="block" style="max-width: 600px; max-height: 600px;">
                <el-image :src="src"></el-image>
              </div>
            </div>
          </template>
          <template slot="extra">
            <el-button type="primary" size="medium">保存下载</el-button>
          </template>
          
        </el-result>

      </el-main> -->

      <router-view></router-view>

      

      <el-footer style="height: 100px;">
        <div class="block">
          <span class="demonstration" >您对本次生成图片效果的满意吗？</span>
          <el-rate v-model="value2" :colors="colors">
          </el-rate>
        </div>
      </el-footer>
    </el-container>




  </div>
</template>


<script>
// 引入组件
import Header from './components/Header'
import Footer from './components/Footer'
import Main from '@/components/Main/index.vue'
import router from './router';
export default {
  name: '',
  components: {
    Header,
    Footer,
    Main,
    router
}

}
</script>

<script>
export default {
    data() {
      return {
        input3: null,
        src: 'https://cube.elemecdn.com/6/94/4d3ea53c084bad6931a56d5158a48jpeg.jpeg',
        value1: null,
        value2: null,
        colors: ['#99A9BF', '#F7BA2A', '#FF9900'],  // 等同于 { 2: '#99A9BF', 4: { value: '#F7BA2A', excluded: true }, 5: '#FF9900' }
        
      }
    },
    methods: {
      errorHanderler() {
        this.src = 'https://cube.elemecdn.com/6/94/4d3ea53c084bad6931a56d5158a48jpeg.jpeg'
      }
    }
  }
</script>


<style lang="scss" scoped></style>

<style>
.el-select-dropdown__item span {
  display: block;
}

.el-select .el-input {
  width: 130px;
}

.input-with-select .el-input-group__prepend {
  background-color: #fff;
}
</style>

<style>
.el-header,
.el-footer {
  background-color: #B3C0D1;
  color: #333;
  text-align: center;
  line-height: 40px;
}

.el-main {
  background-color: #E9EEF3;
  color: #333;
  text-align: center;
  line-height: 60px;
}

body>.el-container {
  margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}
</style>