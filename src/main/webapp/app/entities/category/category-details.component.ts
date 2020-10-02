import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICategory } from '@/shared/model/category.model';
import CategoryService from './category.service';

@Component
export default class CategoryDetails extends Vue {
  @Inject('categoryService') private categoryService: () => CategoryService;
  public category: ICategory = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.categoryId) {
        vm.retrieveCategory(to.params.categoryId);
      }
    });
  }

  public retrieveCategory(categoryId) {
    this.categoryService()
      .find(categoryId)
      .then(res => {
        this.category = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
