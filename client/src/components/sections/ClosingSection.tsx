const ClosingSection = () => (
  <section className="bg-blue-deep py-19 text-white max-[760px]:py-15" aria-labelledby="closing-title">
    <div className="mx-auto flex w-[calc(100%-80px)] max-w-330 items-end justify-between gap-8.75 max-[760px]:block max-[760px]:w-[min(calc(100%-36px),560px)] max-[420px]:w-[calc(100%-32px)]">
      <h2 className="m-0 max-w-185 text-[clamp(32px,3.8vw,54px)] leading-[1.2] font-extrabold tracking-[-.04em] max-[760px]:mb-6.75" id="closing-title">Khám ở đâu, bắt đầu cùng CarePath.</h2>
      <a className="inline-flex shrink-0 items-center gap-5 bg-lime px-5.75 py-4 text-sm font-extrabold text-blue-deep no-underline transition-[background,transform] duration-200 hover:-translate-y-0.75 hover:bg-[#e7ff88] [&_svg]:size-4.5" href="#tim-kiem">
        Thử tìm lịch khám
        <svg viewBox="0 0 20 20" fill="none" aria-hidden="true"><path d="M3 10h14m-6-6 6 6-6 6" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" /></svg>
      </a>
    </div>
  </section>
);

export default ClosingSection;
