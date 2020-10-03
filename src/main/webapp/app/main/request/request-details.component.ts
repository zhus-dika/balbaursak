import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRequest } from '@/shared/model/request.model';
import RequestService from './request.service';

@Component
export default class RequestDetails extends Vue {
  @Inject('requestService') private requestService: () => RequestService;
  public request: IRequest = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.requestId) {
        vm.retrieveRequest(to.params.requestId);
      }
    });
  }

  public retrieveRequest(requestId) {
    this.requestService()
      .find(requestId)
      .then(res => {
        this.request = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
