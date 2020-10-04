import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IFeedback } from '@/shared/model/feedback.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import FeedbackService from '../feedback/feedback.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Feedback extends mixins(AlertMixin) {
  @Inject('feedbackService') public feedbackService: () => FeedbackService;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;
  public param: number = null;

  public feedbacks: IFeedback[] = [];
  public isFetching = false;
  public created() {
    this.param = parseInt(this.$route.params.produceId, 10);
  }
  public mounted(): void {
    this.retrieveAllFeedbacks();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllFeedbacks();
  }

  public retrieveAllFeedbacks(): void {
    this.isFetching = true;
    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
      produceId: this.param
    };
    this.feedbackService()
      .retrieveByProduce(paginationQuery)
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
