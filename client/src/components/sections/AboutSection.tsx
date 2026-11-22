import receptionPhoto from "../../assets/pexels-hospital-reception.jpg";
import calendarPhoto from "../../assets/pexels-clinic-appointment-calendar.jpg";

// Pexels: Pavel Danilyuk (photos 7108325 and 6809657).

const AboutSection = () => (
  <>
    <section
      className="bg-white py-23.75 max-[760px]:py-18.75"
      id="ve-nen-tang"
      aria-labelledby="principle-title"
    >
    <div className="mx-auto grid w-[calc(100%-80px)] max-w-330 grid-cols-2 items-center gap-[11%] max-[760px]:w-[min(calc(100%-36px),560px)] max-[760px]:grid-cols-1 max-[760px]:gap-6.5 max-[420px]:w-[calc(100%-32px)]">
      <div className="grid aspect-[1.3] max-w-118.75 grid-cols-3 gap-2.5 max-[760px]:order-2 max-[760px]:max-w-105" aria-hidden="true">
        <div className="grid place-items-center bg-blue-light text-blue [&_svg]:size-[52%]">
          <svg viewBox="0 0 80 80" fill="none"><path d="M10 40h60M40 10v60" stroke="currentColor" strokeWidth="10" /></svg>
        </div>
        <div className="grid place-items-center bg-blue text-white [&_svg]:size-[52%]">
          <svg viewBox="0 0 80 80" fill="none"><path d="M8 42h57m-17-17 17 17-17 17" stroke="currentColor" strokeWidth="9" strokeLinecap="square" strokeLinejoin="miter" /></svg>
        </div>
        <div className="grid place-items-center bg-lime text-blue [&_svg]:size-[52%]">
          <svg viewBox="0 0 80 80" fill="none"><path d="M12 43 31 61 68 19" stroke="currentColor" strokeWidth="10" strokeLinecap="square" strokeLinejoin="miter" /></svg>
        </div>
      </div>
      <div>
        <h2 className="mb-6 max-w-142.5 text-[clamp(31px,3.25vw,48px)] leading-[1.22] font-extrabold tracking-[-.045em] text-blue-deep" id="principle-title">CarePath là gì?</h2>
        <p className="m-0 max-w-126.25 text-muted">CarePath là nền tảng kết nối lịch khám tại nhiều bệnh viện. Bạn có thể tìm lịch, theo dõi và quản lý các cuộc hẹn ở từng nơi. Mỗi bệnh viện tự quyết định dịch vụ, lịch tiếp nhận, hình thức thanh toán và quy định đổi hủy; việc khám chữa bệnh vẫn do bệnh viện thực hiện.</p>
      </div>
    </div>
    </section>
    <section
      className="border-t border-line bg-blue-light py-19 max-[760px]:py-14"
      id="danh-cho-benh-vien"
      aria-labelledby="hospital-contact-title"
    >
      <div className="mx-auto w-[calc(100%-80px)] max-w-330 max-[760px]:w-[min(calc(100%-36px),560px)] max-[420px]:w-[calc(100%-32px)]">
        <div className="grid grid-cols-[minmax(0,1fr)_minmax(0,1.06fr)] items-center gap-13 max-[760px]:grid-cols-1 max-[760px]:gap-7">
          <div className="max-w-145">
            <h2 className="mb-6 text-[clamp(31px,3.25vw,48px)] leading-[1.22] font-extrabold tracking-[-.04em] text-blue-deep" id="hospital-contact-title">Bệnh viện muốn tiếp nhận đặt lịch qua CarePath?</h2>
            <p className="m-0 text-base leading-[1.8] text-ink">CarePath giúp người bệnh tìm nơi khám và xem các khung tiếp nhận do bệnh viện cung cấp. Bệnh viện vẫn là nơi quyết định dịch vụ, lịch tiếp nhận và quy định áp dụng cho từng cuộc hẹn.</p>
          </div>
          <img className="aspect-[3/2] w-full border border-blue/15 object-cover" src={receptionPhoto} alt="Nhân viên tại quầy tiếp nhận hỗ trợ người đến khám" loading="lazy" />
        </div>
        <div className="mt-12 grid grid-cols-[minmax(0,1.06fr)_minmax(0,1fr)] items-center gap-13 border-t border-blue/20 pt-12 max-[760px]:mt-9 max-[760px]:grid-cols-1 max-[760px]:gap-7 max-[760px]:pt-9">
          <img className="aspect-[3/2] w-full border border-blue/15 object-cover max-[760px]:order-2" src={calendarPhoto} alt="Nhân viên tại quầy tiếp nhận trao đổi với khách về lịch hẹn" loading="lazy" />
          <div className="max-w-130">
            <h3 className="mb-5 text-[clamp(24px,2.45vw,34px)] leading-[1.3] font-extrabold tracking-[-.035em] text-blue-deep">Lịch tiếp nhận theo cách bệnh viện vận hành</h3>
            <p className="m-0 leading-[1.8] text-ink">Bệnh viện chủ động thiết lập dịch vụ, khung giờ và số lượng tiếp nhận. Người bệnh nhìn thấy các lựa chọn phù hợp trước khi đặt lịch; thời gian hiển thị là khung tiếp nhận, không phải giờ bắt đầu khám.</p>
          </div>
        </div>
        <div className="mt-12 grid grid-cols-[minmax(0,1fr)_minmax(0,1.06fr)] items-start gap-13 border-t border-blue/20 pt-10 max-[760px]:mt-9 max-[760px]:grid-cols-1 max-[760px]:gap-5 max-[760px]:pt-8">
          <div className="max-w-125">
            <h3 className="mb-3 text-xl font-extrabold text-blue-deep">Liên hệ dành cho bệnh viện</h3>
            <p className="m-0 text-sm leading-[1.8] text-ink">Bệnh viện quan tâm đến việc kết nối lịch tiếp nhận có thể trao đổi trực tiếp qua email hoặc điện thoại.</p>
          </div>
          <div className="grid gap-4 text-sm text-blue-deep">
            <div className="flex flex-wrap items-baseline justify-between gap-x-5 gap-y-1 border-b border-blue/20 pb-4">
              <span className="font-medium text-ink">Email</span>
              <a className="break-all font-bold underline decoration-blue/40 underline-offset-4 hover:decoration-blue" href="mailto:cuong75201@gmail.com">cuong75201@gmail.com</a>
            </div>
            <div className="flex flex-wrap items-baseline justify-between gap-x-5 gap-y-1 border-b border-blue/20 pb-4">
              <span className="font-medium text-ink">Điện thoại</span>
              <a className="font-bold underline decoration-blue/40 underline-offset-4 hover:decoration-blue" href="tel:+84363589035">0363 589 035</a>
            </div>
            <div className="flex flex-wrap items-center justify-between gap-x-5 gap-y-1">
              <span className="font-medium text-ink">Facebook</span>
              <span className="inline-flex items-center gap-2 font-semibold">
                <svg className="size-5 shrink-0" viewBox="0 0 24 24" fill="none" aria-hidden="true">
                  <rect width="24" height="24" rx="4" fill="#1747a6" />
                  <path d="M14.2 20v-7h2.35l.35-2.8h-2.7V8.4c0-.8.25-1.35 1.35-1.35H17V4.5c-.25-.04-1.1-.1-2.1-.1-2.1 0-3.55 1.3-3.55 3.7v2.1H9V13h2.35v7h2.85Z" fill="white" />
                </svg>
                Sắp cập nhật
              </span>
            </div>
          </div>
        </div>
      </div>
    </section>
  </>
);

export default AboutSection;
