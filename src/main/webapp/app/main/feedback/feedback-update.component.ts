import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import ProduceService from '../produce/produce.service';
import { IProduce } from '@/shared/model/produce.model';

import AlertService from '@/shared/alert/alert.service';
import { IFeedback, Feedback } from '@/shared/model/feedback.model';
import FeedbackService from './feedback.service';

const validations: any = {
  feedback: {
    created: {
      required,
    },
    customer: {
      required,
    },
    vote: {
      required,
      numeric,
      min: minValue(0),
      max: maxValue(5),
    },
    content: {
      required,
    },
    produce: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class FeedbackUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('feedbackService') private feedbackService: () => FeedbackService;
  public feedback: IFeedback = new Feedback();

  @Inject('produceService') private produceService: () => ProduceService;

  public produces: IProduce[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.feedbackId) {
        vm.retrieveFeedback(to.params.feedbackId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.initData();
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }
  public initData() {
    this.feedback.created = new Date();
    const param = this.$route.params.produceId;
    this.produceService().find(parseInt(param, 10)).then(res => this.feedback.produce = res);
  }

  public save(): void {
    this.isSaving = true;
    this.feedbackService()
      .create(this.feedback)
      .then(param => {
        this.isSaving = false;
        this.$router.replace('/');
        const message = this.$t('balbaursakApp.feedback.created', { param: param.id });
        this.alertService().showAlert(message, 'success');
      });
  }

  public retrieveFeedback(feedbackId): void {
    this.feedbackService()
      .find(feedbackId)
      .then(res => {
        this.feedback = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.produceService()
      .retrieve()
      .then(res => {
        this.produces = res.data;
      });
  }
}
