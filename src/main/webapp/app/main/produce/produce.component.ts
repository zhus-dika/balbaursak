import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IProduce } from '@/shared/model/produce.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import JhiDataUtils from '@/shared/data/data-utils.service';

import ProduceService from './produce.service';
import { Category, ICategory } from '@/shared/model/category.model';
import CategoryService from '@/entities/category/category.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Produce extends mixins(JhiDataUtils, AlertMixin) {
  @Inject('produceService') private produceService: () => ProduceService;
  @Inject('categoryService') private categoryService: () => CategoryService;
  private removeId: number = null;
  public itemsPerPage = 5;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;
  public category: ICategory = new Category();
  public produces: IProduce[] = [];
  public categories: ICategory[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllProduces();
    this.retrieveAllCategorys();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllProduces();
  }
  get filteredProduces() {
    return (this.category.id ===  undefined) ? this.produces : this.produces.filter(ele => ele.category.id === this.category.id);
  }
  public retrieveAllProduces(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    this.produceService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.produces = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }
  public retrieveAllCategorys(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    this.categoryService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.categories = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }
  public prepareRemove(instance: IProduce): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeProduce(): void {
    this.produceService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('balbaursakApp.produce.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllProduces();
        this.closeDialog();
      });
  }

  public sort(): Array<any> {
    const result = [this.propOrder + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.propOrder !== 'id') {
      result.push('id');
    }
    return result;
  }

  public loadPage(page: number): void {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  public transition(): void {
    this.retrieveAllProduces();
  }

  public changeOrder(propOrder): void {
    this.propOrder = propOrder;
    this.reverse = !this.reverse;
    this.transition();
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
