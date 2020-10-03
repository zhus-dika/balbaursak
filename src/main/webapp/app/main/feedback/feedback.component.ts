import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IFeedback } from '@/shared/model/feedback.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import FeedbackService from './feedback.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Feedback extends mixins(AlertMixin) {
  @Inject('feedbackService') private feedbackService: () => FeedbackService;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;
  public param;

  public feedbacks: IFeedback[] = [];
  public isFetching = false;

  public mounted(): void {
    this.param = this.$route.params.produceId;
    this.retrieveAllFeedbacks();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllFeedbacks();
  }
  get filteredFeedbacks(): IFeedback[] {
    return this.feedbacks.filter(ele => ele.produce.id === this.param);
  }
  public retrieveAllFeedbacks(): void {
    this.isFetching = true;
    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    this.feedbackService().retrieve(paginationQuery)
      .then(
        res => {
          this.feedbacks = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IFeedback): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeFeedback(): void {
    this.feedbackService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('balbaursakApp.feedback.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllFeedbacks();
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
    this.retrieveAllFeedbacks();
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
