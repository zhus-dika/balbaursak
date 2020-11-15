import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRequestpoint } from '@/shared/model/requestpoint.model';
import RequestpointService from './requestpoint.service';

@Component
export default class RequestpointDetails extends Vue {
  @Inject('requestpointService') private requestpointService: () => RequestpointService;
  public requestpoint: IRequestpoint = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.requestpointId) {
        vm.retrieveRequestpoint(to.params.requestpointId);
      }
    });
  }

  public retrieveRequestpoint(requestpointId) {
    this.requestpointService()
      .find(requestpointId)
      .then(res => {
        this.requestpoint = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
