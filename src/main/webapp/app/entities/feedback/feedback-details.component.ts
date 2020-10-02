import { Component, Vue, Inject } from 'vue-property-decorator';

import { IFeedback } from '@/shared/model/feedback.model';
import FeedbackService from './feedback.service';

@Component
export default class FeedbackDetails extends Vue {
  @Inject('feedbackService') private feedbackService: () => FeedbackService;
  public feedback: IFeedback = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.feedbackId) {
        vm.retrieveFeedback(to.params.feedbackId);
      }
    });
  }

  public retrieveFeedback(feedbackId) {
    this.feedbackService()
      .find(feedbackId)
      .then(res => {
        this.feedback = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
