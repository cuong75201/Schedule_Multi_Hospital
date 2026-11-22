const notes = [
  "Lịch mở và số lượng tiếp nhận do từng bệnh viện quyết định",
  "Thông tin thay đổi phụ thuộc chính sách của nơi khám",
];

const windows = ["08:00 – 09:00", "09:00 – 10:00", "10:00 – 11:00"];

const IntakeWindowsSection = () => (
  <section className="overflow-hidden bg-blue-light py-24.5 max-[760px]:py-18.75" id="khung-gio" aria-labelledby="feature-title">
    <div className="mx-auto grid w-[calc(100%-80px)] max-w-330 grid-cols-2 items-center gap-[10%] max-[760px]:w-[min(calc(100%-36px),560px)] max-[760px]:grid-cols-1 max-[760px]:gap-6.5 max-[420px]:w-[calc(100%-32px)]">
      <div className="[&_p]:mb-7.25 [&_p]:max-w-126.25 [&_p]:text-[#304f7c] [&_ul]:m-0 [&_ul]:max-w-126.25 [&_ul]:list-none [&_ul]:border-t [&_ul]:border-[#a9c0e3] [&_ul]:p-0 [&_li]:flex [&_li]:gap-3.5 [&_li]:border-b [&_li]:border-[#a9c0e3] [&_li]:py-3.75 [&_li]:text-sm [&_li]:font-semibold [&_li_svg]:mt-0.5 [&_li_svg]:size-4.75 [&_li_svg]:shrink-0 [&_li_svg]:text-blue">
        <h2 className="mb-6 max-w-142.5 text-[clamp(31px,3.25vw,48px)] leading-[1.22] font-extrabold tracking-[-.045em] text-blue-deep" id="feature-title">Hiểu đúng về lịch tiếp nhận</h2>
        <p>Khung giờ bạn đặt là khoảng thời gian bệnh viện dự kiến tiếp nhận, không phải cam kết thời điểm bắt đầu khám. Điều này giúp bạn lên kế hoạch thực tế hơn.</p>
        <ul>
          {notes.map((note) => (
            <li key={note}>
              <svg viewBox="0 0 20 20" fill="none" aria-hidden="true"><path d="m3 10 5 5 9-10" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" /></svg>
              {note}
            </li>
          ))}
        </ul>
      </div>
      <div className="relative ml-auto max-w-133.75 bg-white px-8.75 pt-8.5 pb-9.75 shadow-[15px_19px_28px_rgba(16,46,112,.12)] max-[760px]:m-0 max-[760px]:max-w-none max-[420px]:px-5.5 max-[420px]:py-6.25" aria-label="Ví dụ minh họa cách hiển thị khung giờ">
        <div className="mb-6.25 flex items-center justify-between gap-3.75 border-b border-line pb-5.25 [&_strong]:text-[15px] [&_strong]:text-blue-deep max-[420px]:flex-col max-[420px]:items-start max-[420px]:gap-2">
          <strong>Chọn khung tiếp nhận</strong>
          <span className="whitespace-nowrap bg-[#e7efff] px-2.25 py-1.5 text-[11px] font-extrabold text-blue">VÍ DỤ MINH HỌA</span>
        </div>
        <p className="mb-1.5 text-2xl leading-[1.35] font-extrabold tracking-[-.035em] text-blue-deep">Khám nội tổng quát</p>
        <p className="mb-6.25 text-xs text-muted">Lịch mô phỏng · không phải lịch bệnh viện thực tế</p>
        <p className="mb-2.75 text-xs font-extrabold text-blue-deep">Thứ Năm · Ngày minh họa</p>
        <div className="mb-5.25 flex flex-wrap gap-2.25">
          {windows.map((window, index) => (
            <span
              key={window}
              className={index === 1
                ? "border border-blue bg-blue px-3 py-2.25 text-xs font-bold text-white tabular-nums"
                : "border border-[#bad0f3] bg-[#eaf1ff] px-3 py-2.25 text-xs font-bold text-blue-deep tabular-nums"}
            >
              {window}
            </span>
          ))}
        </div>
        <p className="m-0 border-t border-line pt-4.5 text-[11px] leading-[1.65] text-muted">Khung giờ thể hiện thời gian dự kiến tiếp nhận. Thời điểm khám thực tế có thể thay đổi theo hoạt động của bệnh viện.</p>
      </div>
    </div>
  </section>
);

export default IntakeWindowsSection;
