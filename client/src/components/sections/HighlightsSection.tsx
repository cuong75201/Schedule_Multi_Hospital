const highlights = [
  { title: "Nhiều nơi khám", description: "Tìm bệnh viện và dịch vụ theo khu vực, chuyên khoa hoặc nhu cầu của bạn." },
  { title: "Thời gian rõ ràng", description: "Chọn trong các khung tiếp nhận do mỗi bệnh viện mở; đây là thời gian dự kiến tiếp nhận, không phải giờ bắt đầu khám." },
  { title: "Chủ động với lịch hẹn", description: "Theo dõi, đổi hoặc hủy lịch khi chính sách của bệnh viện cho phép." },
];

const HighlightsSection = () => (
  <section className="bg-white py-24.75 max-[760px]:py-18.75" id="cach-hoat-dong" aria-labelledby="intro-title">
    <div className="mx-auto grid w-[calc(100%-80px)] max-w-330 grid-cols-[.86fr_1.14fr] items-start gap-[11%] max-[760px]:w-[min(calc(100%-36px),560px)] max-[760px]:grid-cols-1 max-[760px]:gap-6.5 max-[420px]:w-[calc(100%-32px)]">
      <div>
        <h2 className="mb-6 max-w-142.5 text-[clamp(31px,3.25vw,48px)] leading-[1.22] font-extrabold tracking-[-.045em] text-blue-deep" id="intro-title">Lịch khám, gọn trong một nơi</h2>
        <p className="mb-7.25 max-w-126.25 text-base text-muted">Tìm kiếm và theo dõi các cuộc hẹn ở nhiều bệnh viện mà vẫn thấy rõ thông tin áp dụng tại từng nơi.</p>
        <a className="inline-flex items-center gap-3.25 border-b border-current pb-1.25 text-sm font-extrabold text-blue no-underline hover:gap-4.75 [&_svg]:size-4.25" href="#khung-gio">
          Về lịch tiếp nhận
          <svg viewBox="0 0 20 20" fill="none" aria-hidden="true"><path d="M3 10h14m-6-6 6 6-6 6" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round" /></svg>
        </a>
      </div>
      <div className="border-t-2 border-blue-deep [&_h3]:mb-1.75 [&_h3]:text-lg [&_h3]:leading-[1.4] [&_h3]:font-bold [&_h3]:tracking-tight [&_h3]:text-blue-deep [&_p]:m-0 [&_p]:max-w-110 [&_p]:text-[13px] [&_p]:text-muted" aria-label="Điểm nổi bật của CarePath">
        {highlights.map(({ title, description }) => (
          <div className="grid grid-cols-[minmax(0,1fr)_22px] items-start gap-5 border-b border-line py-6 max-[760px]:grid-cols-[minmax(0,1fr)_20px] max-[760px]:gap-3" key={title}>
            <div><h3>{title}</h3><p>{description}</p></div>
            <svg className="mt-0.5 size-4.75 text-blue" viewBox="0 0 20 20" fill="none" aria-hidden="true"><path d="M3 10h14m-6-6 6 6-6 6" stroke="currentColor" strokeWidth="1.8" /></svg>
          </div>
        ))}
      </div>
    </div>
  </section>
);

export default HighlightsSection;
