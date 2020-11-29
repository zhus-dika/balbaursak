import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IRequestpoint } from '@/shared/model/requestpoint.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import RequestpointService from './requestpoint.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class RequestPoint extends mixins(AlertMixin) {
  @Inject('requestpointService') private requestpointService: () => RequestpointService;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;
  //public requestId = parseInt(this.$route.params.requestId, 10);
  public requestpoints: IRequestpoint[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllRequestpointsByRequest();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllRequestpoints();
  }

  public retrieveAllRequestpoints(): void {
    this.isFetching = true;
    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
      id: parseInt(this.$route.params.requestId, 10)
    };
    this.requestpointService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.requestpoints = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public retrieveAllRequestpointsByRequest(): void {
    this.isFetching = true;
    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
      id: parseInt(this.$route.params.requestId, 10)
    };
    this.requestpointService()
      .retrieveById(paginationQuery)
      .then(
        res => {
          this.requestpoints = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }
  public prepareRemove(instance: IRequestpoint): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeRequestpoint(): void {
    this.requestpointService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('balbaursakApp.requestpoint.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllRequestpoints();
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
    this.retrieveAllRequestpoints();
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
