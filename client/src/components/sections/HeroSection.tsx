import { useEffect, useRef, type FormEvent } from "react";

const routes = {
  hospital: { button: "Theo bệnh viện", label: "Tên bệnh viện", placeholder: "Nhập tên bệnh viện", title: "bệnh viện" },
  specialty: { button: "Theo chuyên khoa", label: "Tên chuyên khoa", placeholder: "Ví dụ: Nội tổng quát", title: "chuyên khoa" },
  service: { button: "Theo dịch vụ", label: "Tên dịch vụ", placeholder: "Nhập tên dịch vụ", title: "dịch vụ" },
  doctor: { button: "Theo bác sĩ", label: "Tên bác sĩ", placeholder: "Nhập tên bác sĩ", title: "bác sĩ" },
} as const;

export type SearchRoute = keyof typeof routes;

type HeroSectionProps = {
  activeRoute: SearchRoute;
  onRouteChange: (route: SearchRoute) => void;
  query: string;
  onQueryChange: (query: string) => void;
  result: string;
  onResultChange: (result: string) => void;
};

const HeroSection = ({ activeRoute, onRouteChange, query, onQueryChange, result, onResultChange }: HeroSectionProps) => {
  const queryRef = useRef<HTMLInputElement>(null);

  useEffect(() => {
    queryRef.current?.setCustomValidity("");
  }, [activeRoute]);

  const chooseRoute = (route: SearchRoute) => {
    onRouteChange(route);
    queryRef.current?.setCustomValidity("");
  };

  const submitSearch = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    if (!query.trim()) {
      queryRef.current?.setCustomValidity("Vui lòng nhập tên để bắt đầu tìm lịch.");
      queryRef.current?.reportValidity();
      return;
    }
    const city = (event.currentTarget.elements.namedItem("city") as HTMLSelectElement).value;
    onResultChange(`Tìm theo ${routes[activeRoute].title}: ${query.trim()} tại ${city}`);
  };

  return (
    <>
     <section className="relative overflow-hidden bg-blue text-white before:pointer-events-none before:absolute before:inset-0 before:bg-[linear-gradient(90deg,transparent_0,transparent_49.94%,rgba(255,255,255,.13)_50%,transparent_50.06%)] max-[760px]:before:hidden" id="dau-trang" aria-labelledby="hero-title">
      <div className="mx-auto w-[calc(100%-80px)] max-w-330 max-[760px]:w-[min(calc(100%-36px),560px)] max-[420px]:w-[calc(100%-32px)] relative grid min-h-156.5 grid-cols-[minmax(0,1.08fr)_minmax(0,.92fr)] items-center gap-[5%] pt-16.75 pb-16.5 max-[1050px]:gap-[3%] max-[760px]:flex max-[760px]:min-h-0 max-[760px]:flex-col max-[760px]:items-stretch max-[760px]:gap-8.5 max-[760px]:pt-13.75 max-[760px]:pb-10.5">
        <div className="relative z-1 max-w-170">
           <h1 className="mb-6.75 text-[clamp(45px,4.85vw,76px)] leading-[1.14] font-extrabold tracking-[-.04em] max-[1050px]:text-[clamp(43px,5vw,63px)] max-[760px]:mb-5 max-[760px]:max-w-140 max-[760px]:text-[clamp(42px,9.4vw,65px)] max-[420px]:text-[40px]" id="hero-title">Một nơi cho <span className="text-lime">nhiều lựa chọn khám.</span></h1>
           <p className="mb-8.75 max-w-140 text-[17px] leading-[1.85] text-[#e4edff] max-[760px]:mb-6.75 max-[760px]:text-[15px] max-[760px]:leading-[1.75]">CarePath giúp bạn tìm và quản lý lịch khám tại nhiều bệnh viện. Bắt đầu từ bệnh viện, chuyên khoa, dịch vụ hoặc bác sĩ khi nơi khám hỗ trợ.</p>
          <div className="mb-3 flex max-w-177.5 flex-wrap gap-2" role="group" aria-label="Chọn cách tìm lịch khám">
              {(Object.keys(routes) as SearchRoute[]).map((route) => (
                <button
                  key={route}
                  className="border border-white/50 bg-transparent px-3 py-2.25 text-xs leading-[1.4] font-bold text-white transition-colors duration-200 hover:bg-white/15 aria-pressed:border-white aria-pressed:bg-white aria-pressed:text-blue-deep"
                  type="button"
                  aria-pressed={activeRoute === route}
                  onClick={() => chooseRoute(route)}
                >
                  {routes[route].button}
                </button>
              ))}
          </div>
          <form className="grid max-w-177.5 grid-cols-[minmax(0,1fr)_minmax(0,1.1fr)_auto] bg-white p-2.25 text-ink shadow-[0_18px_42px_rgba(0,0,0,.16)] max-[760px]:grid-cols-2" id="tim-kiem" aria-label="Tìm lịch khám minh họa" onSubmit={submitSearch}>
              <div className="flex min-w-0 flex-col justify-center px-3.75 py-2 [&_label]:mb-1.25 [&_label]:text-[11px] [&_label]:leading-[1.4] [&_label]:font-extrabold [&_label]:text-blue-deep [&_input]:w-full [&_input]:min-w-0 [&_input]:border-0 [&_input]:bg-transparent [&_input]:p-0 [&_input]:text-[13px] [&_input]:leading-normal [&_input]:font-semibold [&_input]:text-ink [&_input]:caret-blue [&_input]:outline-offset-3 [&_input::placeholder]:text-muted [&_select]:w-full [&_select]:appearance-none [&_select]:border-0 [&_select]:bg-transparent [&_select]:pr-5 [&_select]:text-[13px] [&_select]:leading-normal [&_select]:font-semibold [&_select]:text-ink [&_select]:outline-offset-3 max-[760px]:px-2.5 max-[420px]:[&_select]:text-xs"><label htmlFor="city">Khu vực khám</label><span className="relative"><select className="relative z-10 cursor-pointer" id="city" name="city" required><option value="">Chọn khu vực</option><option value="Hà Nội">Hà Nội</option><option value="TP. Hồ Chí Minh">TP. Hồ Chí Minh</option><option value="Đà Nẵng">Đà Nẵng</option><option value="Khác">Khu vực khác</option></select><svg className="pointer-events-none absolute top-1/2 right-0 size-3.5 -translate-y-1/2 text-blue" viewBox="0 0 14 14" fill="none" aria-hidden="true"><path d="m3 5 4 4 4-4" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round"/></svg></span></div>
              <div className="flex min-w-0 flex-col justify-center border-l border-line px-3.75 py-2 [&_label]:mb-1.25 [&_label]:text-[11px] [&_label]:leading-[1.4] [&_label]:font-extrabold [&_label]:text-blue-deep [&_input]:w-full [&_input]:min-w-0 [&_input]:border-0 [&_input]:bg-transparent [&_input]:p-0 [&_input]:text-[13px] [&_input]:leading-normal [&_input]:font-semibold [&_input]:text-ink [&_input]:caret-blue [&_input]:outline-offset-3 [&_input::placeholder]:text-muted max-[760px]:px-2.5"><label htmlFor="search-query" id="query-label">{routes[activeRoute].label}</label><input ref={queryRef} id="search-query" name="query" type="search" placeholder={routes[activeRoute].placeholder} autoComplete="off" value={query} onChange={(event) => { event.currentTarget.setCustomValidity(""); onQueryChange(event.target.value); onResultChange(""); }} required /></div>
             <button className="flex min-w-34 items-center justify-center gap-2.75 border-0 bg-lime px-4.25 py-3.75 text-[13px] font-extrabold text-blue-deep transition-[background,transform] duration-200 hover:-translate-y-0.5 hover:bg-[#e7ff88] max-[760px]:col-span-2 max-[760px]:mt-2 max-[760px]:p-3.5 [&_svg]:size-4.5 [&_svg]:shrink-0" type="submit">Thử tìm kiếm <svg viewBox="0 0 20 20" fill="none" aria-hidden="true"><path d="M3 10h14m-6-6 6 6-6 6" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/></svg></button>
          </form>

        </div>
        <div className="relative flex min-w-0 self-stretch items-center justify-center before:absolute before:top-[5%] before:right-0 before:bottom-[5%] before:left-[4%] before:border before:border-white/20 max-[760px]:min-h-80 max-[760px]:before:inset-y-0 max-[760px]:before:inset-x-[3%] max-[420px]:min-h-70" aria-hidden="true">
           <div className="relative w-full max-w-118 -rotate-3 filter-[drop-shadow(19px_25px_15px_rgba(5,27,79,.25))] [&_svg]:block [&_svg]:h-auto [&_svg]:w-full max-[1050px]:w-[95%] max-[760px]:w-[85%] max-[760px]:max-w-91.25">
             <svg viewBox="0 0 510 522" xmlns="http://www.w3.org/2000/svg" role="img">
              <rect x="28" y="20" width="450" height="482" fill="#f5f8fc"/>
              <rect x="28" y="20" width="450" height="54" fill="#d7f06b"/>
               <text x="53" y="54" fill="#102e70" fontSize="15" fontWeight="800" fontFamily="sans-serif" letterSpacing="1">CAREPATH · KẾT NỐI LỊCH KHÁM</text>
              <path d="M70 139V413C70 434 87 451 108 451H410" fill="none" stroke="#1747a6" strokeWidth="7" strokeLinecap="square"/>
              <circle cx="70" cy="146" r="19" fill="#1747a6"/><circle cx="70" cy="284" r="19" fill="#1747a6"/><circle cx="70" cy="418" r="19" fill="#1747a6"/>
              <path d="m61 146 7 7 12-15M61 284l7 7 12-15M61 418l7 7 12-15" fill="none" stroke="white" strokeWidth="3.5" strokeLinecap="round" strokeLinejoin="round"/>
               <text x="113" y="126" fill="#1747a6" fontSize="13" fontWeight="700" fontFamily="sans-serif">NƠI KHÁM</text>
                <text x="113" y="156" fill="#102e70" fontSize="25" fontWeight="800" fontFamily="sans-serif">Nhiều bệnh viện</text>
              <path d="M113 184h316" stroke="#cdd9e9"/>
                <text x="113" y="263" fill="#1747a6" fontSize="13" fontWeight="700" fontFamily="sans-serif">TÌM KIẾM</text>
                <text x="113" y="293" fill="#102e70" fontSize="24" fontWeight="800" fontFamily="sans-serif">Theo nhu cầu của bạn</text>
              <path d="M113 322h316" stroke="#cdd9e9"/>
               <text x="113" y="397" fill="#1747a6" fontSize="13" fontWeight="700" fontFamily="sans-serif">LỊCH HẸN</text>
               <text x="113" y="427" fill="#102e70" fontSize="26" fontWeight="800" fontFamily="sans-serif">Chủ động theo dõi</text>
              <path d="m406 443 13 8-13 8" fill="none" stroke="#1747a6" strokeWidth="4" strokeLinecap="round" strokeLinejoin="round"/>
            </svg>
          </div>
           <span className="absolute right-2.25 bottom-3.5 text-[11px] font-bold tracking-[.01em] text-[#e7eeff] max-[1050px]:hidden">Lịch khám tại nhiều nơi, trong một nền tảng.</span>
        </div>
      </div>
    </section>
    {result && (
      <div className="bg-lime text-blue-deep" role="status" aria-live="polite">
        <div className="mx-auto flex w-[calc(100%-80px)] max-w-330 items-start justify-between gap-6 py-6 max-[760px]:w-[min(calc(100%-36px),560px)] max-[420px]:w-[calc(100%-32px)] max-[420px]:flex-col">
          <div><strong className="text-[17px]">{result}</strong><p className="mt-1.25 max-w-200 text-[13px]">Đây là bản giới thiệu nên chưa thể hiển thị bệnh viện hay lịch trống cho lựa chọn này. Khi kết nối dữ liệu, kết quả sẽ phụ thuộc vào lịch và chính sách của từng bệnh viện.</p></div>
          <button className="shrink-0 border border-blue-deep bg-transparent px-3 py-1.75 font-bold text-blue-deep" type="button" onClick={() => onResultChange("")}>Đóng</button>
        </div>
      </div>
    )}
    </>
  )
}

export default HeroSection
